import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { ReadAdminComponent } from './admin/read-admin.component';
import { CreateAdminComponent } from './admin/create-admin.component';
import { UpdateAdminComponent } from './admin/update-admin.component';
import { DeleteAdminComponent } from './admin/delete-admin.component';
import { DeleteMemberComponent } from './member/delete-member.component';
import { UpdateMemberComponent } from './member/update-member.component';
import { ReadMemberComponent } from './member/read-member.component';
import { CreateMemberComponent } from './member/create-member.component';
import { LayoutComponent } from './layout.component';


@NgModule({
  declarations: [
    ReadAdminComponent,
    CreateAdminComponent,
    UpdateAdminComponent,
    DeleteAdminComponent,
    DeleteMemberComponent,
    UpdateMemberComponent,
    ReadMemberComponent,
    CreateMemberComponent,
    LayoutComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
