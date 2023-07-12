import { Body, Controller, Get, Post } from '@nestjs/common';
import { UserRepository } from './user.repository';
import { CreateUserDTO } from './dto/CreateUser.dto';

@Controller('/users')
export class UserController {
  // eslint-disable-next-line prettier/prettier
  constructor(private userRepositry: UserRepository) { }

  @Post()
  async createUser(@Body() userData: CreateUserDTO) {
    this.userRepositry.addUser(userData);
    return userData;
  }

  @Get()
  async getAllUsers() {
    return this.userRepositry.getAllUsers();
  }
}
