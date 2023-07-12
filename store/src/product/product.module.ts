import { Module } from '@nestjs/common';
import { ProductsController } from './product.controller';
import { ProductRepository } from './product.repository';

@Module({
  controllers: [ProductsController],
  providers: [ProductRepository],
})
// eslint-disable-next-line prettier/prettier
export class ProductModule { }
