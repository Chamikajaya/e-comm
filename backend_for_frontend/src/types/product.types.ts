export enum ProductApprovalStatus {
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  REJECTED = "REJECTED",
}

export interface ProductCreateRequest {
  title: string;
  description?: string;
  price: number;
  supplierId: number;
  categoryId: number;
  stockLevel: number;
  isInStock: boolean;
}

export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  supplierId: number;
  categoryId: number;
  stockCount: number;
  imageUrls: string[];
  isInStock: boolean;
  isApproved: ProductApprovalStatus;
  createdAt: string;
  updatedAt: string;
}

export interface PageResponse<T> {
  content: T[];
  currPageNumber: number;
  totalPages: number;
  size: number;
  totalElements: number;
  isFirst: boolean;
  isFinal: boolean;
}

export interface ProductApprovalRequest {
  status: ProductApprovalStatus;
}
