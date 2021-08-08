import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from '@app/auth/auth-routing.module';
import { HeaderComponent, FooterComponent, AlertComponent, ModalComponent } from './index';


@NgModule({
  declarations: [HeaderComponent, FooterComponent, AlertComponent, ModalComponent],
  imports: [CommonModule, AuthRoutingModule],
  exports: [FooterComponent, HeaderComponent, AlertComponent, ModalComponent],
})
export class SharedModule { }
