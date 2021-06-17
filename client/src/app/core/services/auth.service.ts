import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { JwtHelperService } from "@auth0/angular-jwt";

import { IUser } from "../models/user";

import { map } from "rxjs/operators";
import { environment } from "@env/environment";

@Injectable({ providedIn: 'root' })
export class AuthService {

  User: IUser = {
    id: '',
    firstname: '',
    lastname: '',
    username: '',
    email: '',
    registerAsAdmin: false,
    password: ''
  }
  baseUrl: string = environment.baseUrl;
  helper = new JwtHelperService();

  private userSubject: BehaviorSubject<IUser>;
  public user: Observable<IUser>

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject<IUser>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): IUser {
    return this.userSubject.value;
  }

  login(usernameOrEmail: string, password: string): Observable<IUser> {
    return this.http.post<IUser>(`${this.baseUrl}auth/login`, { usernameOrEmail, password })
      .pipe(map((response: any) => {
        if (response.accessToken) {
          localStorage.setItem('token', response.accessToken);
          this.userSubject.next(response);
        }
        return response;
      }))
  }

  register(user: IUser) {
    return this.http.post(`${this.baseUrl}auth/signup`, user);
  }

  getAll() {
    return this.http.get<IUser[]>(`${this.baseUrl}users`);
  }

  getById(id: string) {
    return this.http.get<IUser>(`${this.baseUrl}user/${id}`);
  }

  update(id: string, params: object) {
    return this.http.put(`${this.baseUrl}user/${id}`, params)
      .pipe(map(x => {
        // update stored user if the logged in user updated their own record
        if (id == this.userValue.id) {
          // update local storage
          const user = { ...this.userValue, ...params };
          localStorage.setItem('token', JSON.stringify(user));

          // publish updated user to subscribers
          this.userSubject.next(user);
        }
        return x;
      }));
  }

  delete(id: string) {
    return this.http.delete(`${this.baseUrl}user/${id}`)
      .pipe(map(x => {
        // auto logout if the logged in user deleted their own record
        if (id == this.userValue.id) {
          this.logout();
        }
        return x;
      }));
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token') || '';
    return !this.helper.isTokenExpired(token);
  }

  logout(): void {
    this.User = {
      id: '',
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      registerAsAdmin: false,
      password: ''
    }
    localStorage.removeItem('token');
    this.userSubject.next(this.User);
    this.router.navigate(['login']);
  }

}
