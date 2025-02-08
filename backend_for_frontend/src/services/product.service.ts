import FormData from "form-data";
import { productServiceClient } from "../config/axiosConfig";
import {
  ProductCreateRequest,
  ProductResponse,
  PageResponse,
  ProductApprovalRequest,
} from "../types/product.types";

export class ProductService {
  private readonly basePath = "/product";

  async createProduct(
    request: ProductCreateRequest,
    images: Express.Multer.File[]
  ): Promise<ProductResponse> {
    const formData = new FormData();
    formData.append("product", JSON.stringify(request), {
      contentType: "application/json",
    });

    images.forEach((image) => {
      formData.append("images", image.buffer, {
        filename: image.originalname,
        contentType: image.mimetype,
      });
    });

    const { data } = await productServiceClient.post(this.basePath, formData, {
      headers: {
        ...formData.getHeaders(),
      },
    });
    return data;
  }

  async getAllProducts(
    page: number = 0,
    size: number = 10,
    sortBy: string = "id",
    sortDir: string = "asc",
    query?: string
  ): Promise<PageResponse<ProductResponse>> {
    const { data } = await productServiceClient.get(this.basePath, {
      params: { page, size, sortBy, sortDir, query },
    });
    return data;
  }

  async updateProduct(
    id: number,
    request: ProductCreateRequest
  ): Promise<ProductResponse> {
    const formData = new FormData();
    formData.append("product", JSON.stringify(request), {
      contentType: "application/json",
    });

    const { data } = await productServiceClient.put(
      `${this.basePath}/${id}`,
      formData,
      {
        headers: {
          ...formData.getHeaders(),
        },
      }
    );
    return data;
  }

  async getProductById(id: number): Promise<ProductResponse> {
    const { data } = await productServiceClient.get(`${this.basePath}/${id}`);
    return data;
  }

  async deleteProduct(id: number): Promise<void> {
    await productServiceClient.delete(`${this.basePath}/${id}`);
  }

  async approveProduct(
    id: number,
    request: ProductApprovalRequest
  ): Promise<ProductResponse> {
    const { data } = await productServiceClient.patch(
      `${this.basePath}/${id}/approve`,
      request
    );
    return data;
  }
}
