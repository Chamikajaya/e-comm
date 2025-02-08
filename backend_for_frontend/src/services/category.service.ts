import { productServiceClient } from "../config/axiosConfig";
import {
  CategoryCreateRequest,
  CategoryResponse,
} from "../types/category.types";
import { PageResponse, ProductResponse } from "../types/product.types";

export class CategoryService {
  private readonly basePath = "/category";

  async createCategory(
    request: CategoryCreateRequest
  ): Promise<CategoryResponse> {
    const { data } = await productServiceClient.post(this.basePath, request);
    return data;
  }

  async deleteCategory(categoryId: number): Promise<void> {
    await productServiceClient.delete(`${this.basePath}/${categoryId}`);
  }

  async updateCategory(
    categoryId: number,
    request: CategoryCreateRequest
  ): Promise<CategoryResponse> {
    const { data } = await productServiceClient.put(
      `${this.basePath}/${categoryId}`,
      request
    );
    return data;
  }

  async getAllCategories(): Promise<CategoryResponse[]> {
    const { data } = await productServiceClient.get(this.basePath);
    return data;
  }

  async getProductsByCategory(
    categoryId: number,
    page: number = 0,
    size: number = 8,
    sortBy: string = "id",
    sortDir: string = "asc"
  ): Promise<PageResponse<ProductResponse>> {
    const { data } = await productServiceClient.get(
      `${this.basePath}/${categoryId}/products`,
      {
        params: { page, size, sortBy, sortDir },
      }
    );
    return data;
  }
}
