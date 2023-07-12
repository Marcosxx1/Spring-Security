import { Injectable } from '@nestjs/common';
import { CreateProductDTO } from './productDTO/CreateProductDTO';

@Injectable()
export class ProductRepository {
  private products: CreateProductDTO[] = [];

  async addProduct(product: CreateProductDTO) {
    this.products.push(product);
  }

  async getProducts() {
    return this.products;
  }
}
