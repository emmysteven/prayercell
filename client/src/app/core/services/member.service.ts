import { Injectable } from '@angular/core';
import {Router} from '@angular/router'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {User} from '@app/core/models/user'


const baseUrl = 'http://localhost:3000/api/member';

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private router: Router, private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(baseUrl)
  }

  add(member: User) {
    return this.http.post(`${baseUrl}auth/signup`, member);
  }

}
