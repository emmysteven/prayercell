export class User {
  id?: string = '';
  firstname: string = '';
  lastname: string = '';
  username: string = '';
  email: string = '';
  password: string = '';
  jwtToken?: string = '';
  registerAsAdmin: boolean = false;
}
