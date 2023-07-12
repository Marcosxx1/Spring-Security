import { Module } from '@nestjs/common';
import { UserController } from './user.controller';
import { UserRepository } from './user.repository';

@Module({
  controllers: [UserController],
  providers: [UserRepository],
})
// eslint-disable-next-line prettier/prettier
export class UserModule { }
