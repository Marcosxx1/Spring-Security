import { Module } from '@nestjs/common';
import { UserController } from './user.controller';

@Module({
  imports: [],
  controllers: [UserController],
  providers: [],
})
// eslint-disable-next-line prettier/prettier
export class AppModule { }
