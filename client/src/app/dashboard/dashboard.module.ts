import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { ReadAdminComponent } from './admin/read-admin.component';
import { CreateAdminComponent } from './admin/create-admin.component';
import { UpdateAdminComponent } from './admin/update-admin.component';
import { DeleteAdminComponent } from './admin/delete-admin.component';
import { DeleteMemberComponent } from '@app/dashboard/member';
import { UpdateMemberComponent } from '@app/dashboard/member';
import { ListMemberComponent } from '@app/dashboard/member';
import { addMemberComponent } from '@app/dashboard/member';
import { LayoutComponent } from './layout.component';


@NgModule({
  declarations: [
    ReadAdminComponent,
    CreateAdminComponent,
    UpdateAdminComponent,
    DeleteAdminComponent,
    DeleteMemberComponent,
    UpdateMemberComponent,
    ListMemberComponent,
    addMemberComponent,
    LayoutComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
