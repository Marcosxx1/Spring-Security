import { Injectable } from '@nestjs/common';
import { CreateUserDTO } from './dto/CreateUser.dto';

/* Um privider é um serviço que pode ser injetado em outros serviços,
 * como por exemplo o UserRepository, que é um serviço que tem acesso a um
 * banco de dados, por exemplo.
 *
 * OU
 *
 * Um provider no Nestjs é qualquer classe que esteja anotada com @Injectable
 *
 *  */

@Injectable()
export class UserRepository {
  private users: CreateUserDTO[] = [];

  async addUser(user: CreateUserDTO) {
    this.users.push(user);
    console.log(this.users);
  }
  async getAllUsers(): Promise<CreateUserDTO[]> {
    return this.users;
  }
}
