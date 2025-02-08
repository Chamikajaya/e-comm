import { Request, Response } from "express";
import { CategoryService } from "../services/category.service";
import { asyncHandler } from "../utils/error.handler";

export class CategoryController {
  constructor(private readonly categoryService: CategoryService) {}

  createCategory = asyncHandler(async (req: Request, res: Response) => {
    const category = await this.categoryService.createCategory(req.body);
    res.status(201).json(category);
  });

  deleteCategory = asyncHandler(async (req: Request, res: Response) => {
    await this.categoryService.deleteCategory(Number(req.params.id));
    res.status(204).send();
  });

  updateCategory = asyncHandler(async (req: Request, res: Response) => {
    const category = await this.categoryService.updateCategory(
      Number(req.params.id),
      req.body
    );
    res.json(category);
  });

  getAllCategories = asyncHandler(async (req: Request, res: Response) => {
    const categories = await this.categoryService.getAllCategories();
    res.json(categories);
  });

  getProductsByCategory = asyncHandler(async (req: Request, res: Response) => {
    const { page = 0, size = 8, sortBy = "id", sortDir = "asc" } = req.query;

    const products = await this.categoryService.getProductsByCategory(
      Number(req.params.id),
      Number(page),
      Number(size),
      String(sortBy),
      String(sortDir)
    );
    res.json(products);
  });
}
