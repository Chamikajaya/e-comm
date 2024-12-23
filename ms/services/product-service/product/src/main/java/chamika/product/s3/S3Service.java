package chamika.product.s3;

import chamika.product.exception.ImageUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 5MB
    private static final String[] ALLOWED_CONTENT_TYPES = {"image/jpeg", "image/png", "image/jpg"};

    public String uploadImage(MultipartFile file) {

        validateImage(file);

        String fileName = generateFileName(file);

        log.info("Uploading file: {} to bucket: {}", fileName, bucketName);

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            String imageUrl = generateImageUrl(fileName);

            log.info("Successfully uploaded image. URL: {}", imageUrl);

            return imageUrl;

        } catch (S3Exception e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new ImageUploadException("Failed to upload image to S3", e);
        } catch (IOException e) {
            log.error("Error reading file contents: {}", e.getMessage());
            throw new ImageUploadException("Failed to read image contents", e);
        }
    }

    public void deleteImage(String imageUrl) {
        try {

            String key = extractKeyFromUrl(imageUrl);

            log.info("Deleting image with key: {} from bucket: {}", key, bucketName);

            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(request);

            log.info("Successfully deleted image with key: {}", key);

        } catch (S3Exception e) {
            log.error("Error deleting file from S3: {}", e.getMessage());
            throw new ImageUploadException("Failed to delete image from S3", e);
        }
    }

    private void validateImage(MultipartFile file) {

        if (file.isEmpty()) {
            throw new ImageUploadException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ImageUploadException("File size exceeds maximum limit of 5MB");
        }

        boolean isValidContentType = false;
        for (String type : ALLOWED_CONTENT_TYPES) {
            if (type.equals(file.getContentType())) {
                isValidContentType = true;
                break;
            }
        }

        if (!isValidContentType) {
            throw new ImageUploadException("Invalid file type. Only JPEG, JPG and PNG are allowed");
        }
    }

    private String generateFileName(MultipartFile file) {
        return UUID.randomUUID() + "_" + file.getOriginalFilename();
    }

    private String generateImageUrl(String fileName) {
        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
    }

    private String extractKeyFromUrl(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}