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