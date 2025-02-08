export interface CategoryCreateRequest {
  name: string;
  description?: string;
}

export interface CategoryResponse {
  id: number;
  title: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}
