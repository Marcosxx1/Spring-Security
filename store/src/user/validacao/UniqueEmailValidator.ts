import {
  ValidationOptions,
  ValidatorConstraint,
  ValidatorConstraintInterface,
  registerDecorator,
} from 'class-validator';
import { UserRepository } from '../user.repository';
import { Injectable } from '@nestjs/common';

@Injectable()
@ValidatorConstraint({ async: true })
export class UniqueEmailValidator implements ValidatorConstraintInterface {
  // eslint-disable-next-line prettier/prettier
  constructor(private userRepository: UserRepository) { }

  async validate(value: any): Promise<boolean> {
    const UserWithEmailExists = await this.userRepository.existsWithEmail(
      value,
    );
    return !UserWithEmailExists;
  }
}

export const emailIsUnique = (validationOptions: ValidationOptions) => {
  // eslint-disable-next-line @typescript-eslint/ban-types
  return (object: Object, propertyName: string) => {
    registerDecorator({
      target: object.constructor,
      propertyName: propertyName,
      options: validationOptions,
      constraints: [],
      validator: UniqueEmailValidator,
    });
  };
};
