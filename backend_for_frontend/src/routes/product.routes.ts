// routes/product.routes.ts
import { Router } from "express";
import multer from "multer";
import { ProductController } from "../controllers/product.controller";
import { ProductService } from "../services/product.service";
import { CategoryController } from "../controllers/category.controller";
import { CategoryService } from "../services/category.service";

// Configure multer for memory storage
// TODO: Need Multer if we do upload images through BFF ?
const storage = multer.memoryStorage();
const upload = multer({
  storage,
  limits: {
    fileSize: 5 * 1024 * 1024, // 5MB limit per file
    files: 5, // Maximum 5 files per request
  },
});

const productService = new ProductService();
const categoryService = new CategoryService();

const productController = new ProductController(productService);
const categoryController = new CategoryController(categoryService);

const router = Router();

// Product routes
router.post(
  "/product",
  upload.array("images", 5), // Handle up to 5 image uploads
  productController.createProduct
);

router.get("/product", productController.getAllProducts);
router.get("/product/:id", productController.getProductById);
router.put(
  "/product/:id",
  upload.array("images", 5),
  productController.updateProduct
);
router.delete("/product/:id", productController.deleteProduct);
router.patch("/product/:id/approve", productController.approveProduct);

// Category routes
router.post("/category", categoryController.createCategory);
router.get("/category", categoryController.getAllCategories);
router.get("/category/:id/products", categoryController.getProductsByCategory);
router.put("/category/:id", categoryController.updateCategory);
router.delete("/category/:id", categoryController.deleteCategory);

export default router;
