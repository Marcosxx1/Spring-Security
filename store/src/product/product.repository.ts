import { Injectable } from '@nestjs/common';

@Injectable()
export class ProductRepository {
  private products: string[] = [];

  async addProduct(product: string) {
    this.products.push(product);
  }

  async getProducts() {
    return this.products;
  }
}
