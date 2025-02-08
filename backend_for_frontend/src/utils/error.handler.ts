import { Request, Response, NextFunction } from "express";
import axios, { AxiosError } from "axios";

// Custom Error Class
export class AppError extends Error {
  constructor(
    public statusCode: number,
    public message: string,
    public errors?: any[]
  ) {
    super(message);
    Object.setPrototypeOf(this, AppError.prototype); // for ensuring that AppError instances are correctly recognized as instances of AppError
  }
}

export const errorHandler = (
  error: Error | AppError | AxiosError,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (axios.isAxiosError(error)) {
    // Handle Axios errors from microservice calls
    const statusCode = error.response?.status || 500;
    const message = error.response?.data?.message || "Internal Server Error";
    const errors = error.response?.data?.errors;

    return res.status(statusCode).json({
      status: "error",
      message,
      errors,
    });
  }

  if (error instanceof AppError) {
    return res.status(error.statusCode).json({
      status: "error",
      message: error.message,
      errors: error.errors,
    });
  }

  // Handle unexpected errors
  console.error("Unexpected error:", error);
  return res.status(500).json({
    status: "error",
    message: "Internal Server Error",
  });
};

// using this wrapper we can eliminate the need for try-catch blocks in our controller methods
export const asyncHandler = (fn: Function) => {
  return (req: Request, res: Response, next: NextFunction) => {
    Promise.resolve(fn(req, res, next)).catch(next);
  };
};
