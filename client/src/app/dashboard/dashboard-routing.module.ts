import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '@app/dashboard/layout.component'
import { ReadMemberComponent } from '@app/dashboard/member/read-member.component'

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: 'member', component: ReadMemberComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
