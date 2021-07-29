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
export class AdminService {

  constructor(private router: Router, private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(`${BASE_URL}api/admin`)
  }

  getById(id: string) {
    return this.http.get(`${BASE_URL}api/admin/${id}`)
  }

  add(admin: User) {
    return this.http.post(`${BASE_URL}api/admin`, admin);
  }

  update(id: string, admin: User) {
    admin.id = id
    return this.http.put(`${BASE_URL}api/admin`, admin);
  }

}
