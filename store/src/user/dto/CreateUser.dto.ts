import { IsEmail, IsNotEmpty, MinLength } from 'class-validator';
import { emailIsUnique } from '../validacao/UniqueEmailValidator';

export class CreateUserDTO {
  @IsNotEmpty({ message: 'Name is required' })
  name: string;

  @IsEmail(undefined, { message: 'Email is not valid' })
  @emailIsUnique({ message: 'Email is already in use' })
  email: string;

  @MinLength(6, { message: 'Password is too short' })
  password: string;
}
