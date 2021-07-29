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
}
