import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { DashboardRoutingModule } from './dashboard-routing.module';
import {
  AddAdminComponent,
  ListAdminComponent,
  AddEditAdminComponent,
  DeleteAdminComponent,
} from '@app/dashboard/admin';

import {
  ListMemberComponent,
  AddEditMemberComponent,
  DeleteMemberComponent
} from '@app/dashboard/member';

import { LayoutComponent } from './layout.component';


@NgModule({
  declarations: [
    AddAdminComponent,
    ListAdminComponent,
    AddEditAdminComponent,
    DeleteAdminComponent,
    ListMemberComponent,
    AddEditMemberComponent,
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
