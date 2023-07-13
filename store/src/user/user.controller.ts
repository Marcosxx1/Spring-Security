import { Body, Controller, Get, Post } from '@nestjs/common';
import { UserRepository } from './user.repository';
import { CreateUserDTO } from './dto/CreateUser.dto';
import { UserEntity } from './user.entity';
import { v4 as uuid } from 'uuid';
import { UserListDTO } from './dto/UserList.dto';

@Controller('/users')
export class UserController {
  // eslint-disable-next-line prettier/prettier
  constructor(private userRepositry: UserRepository) { }

  @Post()
  async createUser(@Body() userData: CreateUserDTO) {
    const userEntity = new UserEntity();
    userEntity.name = userData.name;
    userEntity.email = userData.email;
    userEntity.password = userData.password;
    userEntity.id = uuid();

    this.userRepositry.addUser(userEntity);
    return { id: userEntity.id, message: 'User created successfully' };
  }

  @Get()
  async getAllUsers() {
    const savedUsers = await this.userRepositry.getAllUsers();
    const userList = savedUsers.map(
      (user) => new UserListDTO(user.id, user.name),
    );
    return userList;
  }
}
