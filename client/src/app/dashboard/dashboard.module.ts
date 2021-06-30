import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { DashboardRoutingModule } from './dashboard-routing.module';
import {
  AddAdminComponent,
  ListAdminComponent,
  UpdateAdminComponent,
  DeleteAdminComponent,
} from '@app/dashboard/admin';

import {
  addMemberComponent,
  ListMemberComponent,
  UpdateMemberComponent,
  DeleteMemberComponent
} from '@app/dashboard/member';

import { LayoutComponent } from './layout.component';


@NgModule({
  declarations: [
    AddAdminComponent,
    ListAdminComponent,
    UpdateAdminComponent,
    DeleteAdminComponent,
    addMemberComponent,
    ListMemberComponent,
    UpdateMemberComponent,
    DeleteMemberComponent,
    LayoutComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
