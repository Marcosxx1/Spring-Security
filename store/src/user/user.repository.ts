import { Injectable } from '@nestjs/common';
import { CreateUserDTO } from './dto/CreateUser.dto';
import { UserEntity } from './user.entity';

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
  private users: UserEntity[] = [];

  async addUser(user: UserEntity) {
    this.users.push(user);
  }
  async getAllUsers(): Promise<UserEntity[]> {
    return this.users;
  }

  async existsWithEmail(email: string) {
    const possibleUser = this.users.find((user) => user.email === email);
    return possibleUser !== undefined;
  }
}
