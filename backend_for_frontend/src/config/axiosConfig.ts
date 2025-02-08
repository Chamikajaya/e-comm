import axios from "axios";

// Create an instance for the product service
export const productServiceClient = axios.create({
  baseURL: process.env.PRODUCT_SERVICE_URL || "http://localhost:8080/api/v1",
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add response interceptor for consistent error handling
productServiceClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // Log errors for monitoring
    console.error("API Error:", {
      url: error.config?.url,
      method: error.config?.method,
      status: error.response?.status,
      error: error.response?.data,
    });
    return Promise.reject(error);
  }
);
