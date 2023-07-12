import { Body, Controller, Get, Post } from '@nestjs/common';
import { ProductRepository } from './product.repository';

@Controller('/products')
export class ProductsController {
  // eslint-disable-next-line prettier/prettier
  constructor(private productRepository: ProductRepository) { }

  @Post()
  async createProduct(@Body() productData) {
    this.productRepository.addProduct(productData);
    return productData;
  }

  @Get()
  async getAllProjects() {
    return this.productRepository.getProducts();
  }
}
