export class User  {
  id?: string;
  firstname?: string;
  lastname?: string;
  username?: string;
  phoneNumber?: number;
  email?: string;
  password?: string;
  accessToken?: string;
  refreshToken?: string;
  registerAsAdmin?: boolean = false;
}
