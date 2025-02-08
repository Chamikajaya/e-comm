import dotenv from "dotenv";
import express from "express";
import cors from "cors";
import compression from "compression";
import { errorHandler } from "./utils/error.handler";
import productRoutes from "./routes/product.routes";
import healthRoutes from "./routes/health.routes";

dotenv.config();

const app = express();

// Configure CORS
app.use(
  cors({
    origin: process.env.ALLOWED_ORIGINS?.split(",") || "*",
    methods: ["GET", "POST", "PUT", "DELETE", "PATCH"],
    allowedHeaders: ["Content-Type", "Authorization"],
  })
);

// Configure middleware
app.use(compression()); // Compress responses
app.use(express.json({ limit: "10mb" })); // Parse JSON bodies
app.use(express.urlencoded({ extended: true, limit: "10mb" }));

// Mount routes
app.use("/api/v1", productRoutes);
app.use("/", healthRoutes);

// Error handling middleware (should be last)
// TODO: Fix the error with the errorHandler TYPE error - otherwise error handling will not work
// app.use(errorHandler);

// Handle unhandled routes
app.use((req, res) => {
  res.status(404).json({
    status: "error",
    message: `Cannot ${req.method} ${req.url}`,
  });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`🚀 BFF Server running on port ${PORT}`);
  console.log(`Health check available at http://localhost:${PORT}/health`);
});

// // Handle unhandled promise rejections
// process.on("unhandledRejection", (err) => {
//   console.error("Unhandled Promise Rejection:", err);
//   // In production, we might want to exit the process here
//   // process.exit(1);
// });

export default app;
