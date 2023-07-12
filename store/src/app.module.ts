import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';
import { ProductModule } from './product/product.module';

@Module({
  imports: [UserModule, ProductModule],
  providers: [],
})
// eslint-disable-next-line prettier/prettier
export class AppModule { }
