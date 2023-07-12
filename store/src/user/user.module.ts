import { Module } from '@nestjs/common';
import { UserController } from './user.controller';
import { UserRepository } from './user.repository';
import { UniqueEmailValidator } from './validacao/UniqueEmailValidator';

@Module({
  controllers: [UserController],
  providers: [UserRepository, UniqueEmailValidator],
})
// eslint-disable-next-line prettier/prettier
export class UserModule { }
