import { Module } from '@nestjs/common';
import { UserModule } from './user/user.module';

@Module({
  imports: [UserModule],
  providers: [],
})
// eslint-disable-next-line prettier/prettier
export class AppModule { }
