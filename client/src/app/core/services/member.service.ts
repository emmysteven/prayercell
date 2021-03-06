import { Injectable } from '@angular/core';
import {Router} from '@angular/router'
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs'
import {User} from '@app/core/models/user'
import { environment } from "@env/environment";

// const httpOptions = { withCredentials: true };
const BASE_URL = environment.baseUrl;

@Injectable({
  providedIn: 'root'
})
export class MemberService {

  constructor(private router: Router, private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(`${BASE_URL}api/member`)
  }

  getById(id: string) {
    return this.http.get(`${BASE_URL}api/member/${id}`)
  }

  add(member: User) {
    return this.http.post(`${BASE_URL}api/member`, member);
  }

  update(id: string, member: User) {
    member.id = id
    return this.http.put(`${BASE_URL}api/member`, member);
  }

  delete(id: string) {
    return this.http.delete(`${BASE_URL}api/member/${id}`);
  }

}
