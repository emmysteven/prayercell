import { Component, OnInit } from '@angular/core';
import {MemberService} from '@app/core/services/member.service'

@Component({
  selector: 'app-read-member',
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
      <tr *ngFor="let member of members">
        <td>{{ member.firstname }}</td>
        <td>{{ member.lastname }}</td>
        <td>{{ member.dob }}</td>
        <td>
          <div class="dropdown">
            <button
              class="btn btn-default shadow-none dropdown-toggle"
              type="button"
              data-toggle="dropdown"
            >
              <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
              <li>
                <a class="btn btn-light" routerLink="'/update/' + member.id">
                  <img src="../../../assets/update.svg" alt="update" />
                </a>
              </li>
              <li>
                <button class="btn btn-light">
                  btn-light<img src="../../../assets/delete.svg" alt="delete" />
                </button>
              </li>
              <li>
                <a class="btn btn-light" routerLink="'/read/' + member.id">
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
    `.add {
      width: 35px;
      height: 35px;
      margin: 0.2rem 1.3rem 1rem 1.3rem;
    }

    .dropdown-menu {
      min-width: 0 !important;
      border: none !important;
      background-color: #f5f5f5 !important;
    }

    .search {
      /* width: 53rem !important; */
      margin-left: 0.2rem !important;
    }

    .search-control {
      margin-top: 2.5rem;
      width: 89.9%;
    }

    .table {
      width: 90% !important;
    }

    .btn-light {
      margin: -0.2rem 2px 2px 2px !important;
    }

    .table a {
      display: inline-block;
      text-decoration: none;
    }

    .table img {
      width: 18px;
      height: 18px;
    }

    /* .table th:nth-child(4) {
      text-align: center;
    } */
    `
  ]
})
export class ReadMemberComponent implements OnInit {

  members: any;

  constructor(private memberService: MemberService) { }

  ngOnInit(): void {
    this.fetchMembers();
  }

  fetchMembers(): void {
    this.memberService.getAll()
      .subscribe(
        data => {
         this.members = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });

  }

}
