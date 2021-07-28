import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from '@app/dashboard/layout.component'
import {
  ListMemberComponent,
  AddEditMemberComponent
} from '@app/dashboard/member'
import { ListAdminComponent } from '@app/dashboard/admin'

const routes: Routes = [
  {
    path: '', component: LayoutComponent,
    children: [
      { path: 'admin', component: ListAdminComponent },
      { path: 'member', component: ListMemberComponent },
      { path: 'member/add', component: AddEditMemberComponent },
      { path: 'member/edit/:id', component: AddEditMemberComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
