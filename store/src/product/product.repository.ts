import { Injectable } from '@nestjs/common';

@Injectable()
export class ProductRepository {
  private products: string[] = [];

  async addProduct(product: string): Promise<void> {
    this.products.push(product);
  }

  async getProducts(): Promise<string[]> {
    return this.products;
  }
}
