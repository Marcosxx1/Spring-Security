import { Module } from '@nestjs/common';
import { UserController } from './user.controller';

@Module({
  controllers: [UserController],
})
// eslint-disable-next-line prettier/prettier
export class UserModule { }
