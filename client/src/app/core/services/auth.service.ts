import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";
import { JwtHelperService } from "@auth0/angular-jwt";

import { User } from "../models/user";
import { environment } from "@env/environment";

const httpOptions = { withCredentials: true };
const BASE_URL = environment.baseUrl;

@Injectable({ providedIn: 'root' })
export class AuthService {
  jwtHelper = new JwtHelperService();

  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>

  constructor(
    private router: Router,
    private http: HttpClient,
  ) {
    this.userSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('user')!));
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User {
    return this.userSubject.value;
  }

  login(usernameOrEmail: string, password: string): Observable<User> {
    return this.http.post<User>(`${BASE_URL}auth/login`, { usernameOrEmail, password })
      .pipe(map((user) => {
        localStorage.setItem('user', JSON.stringify(user));
        this.userSubject.next(user);
        // this.startRefreshTokenTimer();
        return user;
      }));
  }

  register(user: User) {
    return this.http.post(`${BASE_URL}auth/signup`, user);
  }

  getAll() {
    return this.http.get<User[]>(`${BASE_URL}users`);
  }

  getById(id: string) {
    return this.http.get<User>(`${BASE_URL}user/${id}`);
  }

  update(id: string, params: object) {
    return this.http.put(`${BASE_URL}user/${id}`, params)
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
    return this.http.delete(`${BASE_URL}user/${id}`)
      .pipe(map(x => {
        // auto logout if the logged in user deleted their own record
        if (id == this.userValue.id) {
          this.logout();
        }
        return x;
      }));
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('user') || '';
    return !this.jwtHelper.isTokenExpired(token);
  }

  logout():void {
    this.http.post<any>(`${BASE_URL}auth/revoke-token`, {}, httpOptions).subscribe();
    this.stopRefreshTokenTimer();
    localStorage.removeItem('user');
    this.userSubject.next({});
    this.router.navigate(['/login']);
  }

  refreshToken(refreshToken: string) {
    return this.http.post<any>(`${BASE_URL}auth/refresh_token`, {refreshToken}, httpOptions)
      .pipe(map((user) => {
        this.userSubject.next(user);
        this.startRefreshTokenTimer();
        return user;
      }));
  }

  // helper methods

  private refreshTokenTimeout: any;

  private startRefreshTokenTimer() {
    // parse json object from base64 encoded jwt token
    const accessToken = this.jwtHelper.decodeToken(this.userValue.accessToken);

    // set a timeout to refresh the token a minute before it expires
    const expires = new Date(accessToken.exp * 1000);
    const timeout = expires.getTime() - Date.now() - (60 * 1000);
    this.refreshTokenTimeout = setTimeout(() => this.refreshToken(accessToken).subscribe(), timeout);
  }

  private stopRefreshTokenTimer() {
    clearTimeout(this.refreshTokenTimeout);
  }

}
