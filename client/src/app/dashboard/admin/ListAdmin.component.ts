import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'

@Component({
  selector: 'app-read-admin',
  template: `
    <p>
      it worked
    </p>
  `,
  styles: [
  ]
})

export class ListAdminComponent implements OnInit {
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  // getAll(): Observable<any> {
  //   return this.http.get(this.baseUrl);
  // }

}
