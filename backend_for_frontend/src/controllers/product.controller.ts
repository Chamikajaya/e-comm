import { Request, Response } from "express";
import { ProductService } from "../services/product.service";
import { asyncHandler } from "../utils/error.handler";

export class ProductController {
  constructor(private readonly productService: ProductService) {}

  createProduct = asyncHandler(async (req: Request, res: Response) => {
    const product = await this.productService.createProduct(
      req.body.product,
      req.files as Express.Multer.File[]
    );
    res.status(201).json(product);
  });

  getAllProducts = asyncHandler(async (req: Request, res: Response) => {
    const {
      page = 0,
      size = 10,
      sortBy = "id",
      sortDir = "asc",
      query,
    } = req.query;

    const products = await this.productService.getAllProducts(
      Number(page),
      Number(size),
      String(sortBy),
      String(sortDir),
      query as string | undefined
    );
    res.json(products);
  });

  updateProduct = asyncHandler(async (req: Request, res: Response) => {
    const product = await this.productService.updateProduct(
      Number(req.params.id),
      req.body.product
    );
    res.json(product);
  });

  getProductById = asyncHandler(async (req: Request, res: Response) => {
    const product = await this.productService.getProductById(
      Number(req.params.id)
    );
    res.json(product);
  });

  deleteProduct = asyncHandler(async (req: Request, res: Response) => {
    await this.productService.deleteProduct(Number(req.params.id));
    res.status(204).send();
  });

  approveProduct = asyncHandler(async (req: Request, res: Response) => {
    const product = await this.productService.approveProduct(
      Number(req.params.id),
      req.body
    );
    res.json(product);
  });
}
