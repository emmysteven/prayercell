import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent, FooterComponent } from './components';
import {AuthRoutingModule} from '@app/auth/auth-routing.module'


@NgModule({
  declarations: [HeaderComponent, FooterComponent],
  imports: [CommonModule, AuthRoutingModule],
  exports: [FooterComponent, HeaderComponent],
})
export class SharedModule { }
