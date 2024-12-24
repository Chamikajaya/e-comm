package chamika.user.exception;

import java.time.LocalDateTime;
import java.util.List;

public record APIError(
        String path,
        String message,
        List<String> errors,
        int statusCode,
        LocalDateTime timeStamp
) {
}