import { Router } from "express";
import { productServiceClient } from "../config/axiosConfig";

const router = Router();

interface HealthStatus {
  status: "up" | "down";
  timestamp: string;
  services: {
    productService: {
      status: "up" | "down";
      latency?: number;
      error?: string;
    };
    // TODO: Need to add other services later
  };
}

// Health check endpoint that also verifies connection to the product service
// router.get("/health", async (req, res) => {
//   const healthStatus: HealthStatus = {
//     status: "up",
//     timestamp: new Date().toISOString(),
//     services: {
//       productService: {
//         status: "down",
//       },
//     },
//   };

//   try {
//     // Check product service health by timing a simple request
//     const start = Date.now();
//     await productServiceClient.get("/actuator/health");  // TODO: Need to add actuator endpoint later in Microservices
//     const latency = Date.now() - start;

//     healthStatus.services.productService = {
//       status: "up",
//       latency,
//     };
//   } catch (error) {
//     healthStatus.status = "down";
//     healthStatus.services.productService = {
//       status: "down",
//       error: error instanceof Error ? error.message : "Unknown error",
//     };
//   }

//   const statusCode = healthStatus.status === "up" ? 200 : 503;
//   res.status(statusCode).json(healthStatus);
// });

// Simple ping endpoint for basic availability checks
router.get("/ping", (req, res) => {
  res.status(200).json({
    message: "pong",
    timestamp: new Date().toISOString(),
  });
});

export default router;
