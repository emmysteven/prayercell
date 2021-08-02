import { Component, OnInit } from '@angular/core';
import {AdminService} from '@app/core/services'
import {first} from 'rxjs/operators'

@Component({
  selector: 'app-read-admin',
  template: `
    <div class="search-control d-flex flex-row">
      <a routerLink="/admin/add">
        <img src="../../../assets/add.svg" class="add" alt="Add" />
      </a>
      <input class="search form-control" placeholder="Firstname or Lastname" />
    </div>
    <table class="table table-hover">
      <thead>
      <tr>
        <th>Firstname</th>
        <th>Lastname</th>
        <th>DOB</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <tr *ngFor="let admin of admins">
        <td>{{ admin.firstname }}</td>
        <td>{{ admin.lastname }}</td>
        <td>{{ admin.birthDate }}</td>
        <td>
          <div class="dropdown">
            <button
              class="btn btn-default shadow-none dropdown-toggle"
              type="button"
              data-bs-toggle="dropdown"
            >
              <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
              <li>
                <a class="btn btn-light" routerLink="/member/edit/{{admin.id}}">
                  <img src="../../../assets/update.svg" alt="edit" />
                </a>
              </li>
              <li>
                <button (click)="deleteAdmin(admin.id)" class="btn btn-light" [disabled]="admin.isDeleting">
                  <span *ngIf="admin.isDeleting" class="spinner-border spinner-border-sm"></span>
                  <span *ngIf="!admin.isDeleting">
                    <img src="../../../assets/delete.svg" alt="delete" />
                  </span>
                </button>
              </li>
              <li>
                <a class="btn btn-light" routerLink="/read/{{admin.id}}">
                  <img src="../../../assets/view.svg" alt="view" />
                </a>
              </li>
            </ul>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
  `,
  styles: [
  ]
})

export class ListAdminComponent implements OnInit {
  admins: any;

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.fetchAdmins();
  }

  fetchAdmins(): void {
    this.adminService.getAll().subscribe(
      data => {
        this.admins = data;
        console.log(data);
      },
      error => {
        console.log(error);
      });

  }

  deleteAdmin(id: any) {
    const admin = this.admins.find((x: any) => x.id === id);
    if (!admin) return;
    admin.isDeleting = true;
    this.adminService.delete(id).pipe(first())
      .subscribe(() => this.admins = this.admins.filter((x: any) => x.id !== id));
  }

}
