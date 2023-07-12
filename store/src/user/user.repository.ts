import { Injectable } from '@nestjs/common';

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
  private users: string[] = [];

  async addUser(user: string) {
    this.users.push(user);
    console.log(this.users);
  }
  async getAllUsers(): Promise<string[]> {
    return this.users;
  }
}
