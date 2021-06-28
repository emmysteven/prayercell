import { Component, OnInit } from '@angular/core';
import {MemberService} from '@app/core/services/member.service'

@Component({
  selector: 'app-read-member',
  template: `
    <p>
      read-member works!
    </p>
    <div *ngFor="let member of members">
      <b>{{member.firstname}}</b><br/>
      <b>{{member.lastname}}</b><br/>
      <b>{{member.email}}</b>
    </div>
  `,
  styles: [
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
