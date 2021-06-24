import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { CreateComponent } from './member/create/create.component';
import { ReadComponent } from './member/read/read.component';
import { UpdateComponent } from './member/update/update.component';
import { DeleteComponent } from './member/delete/delete.component';


@NgModule({
  declarations: [
    CreateComponent,
    ReadComponent,
    UpdateComponent,
    DeleteComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule
  ]
})
export class DashboardModule { }
