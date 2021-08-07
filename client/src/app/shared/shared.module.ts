import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthRoutingModule } from '@app/auth/auth-routing.module';
import { HeaderComponent, FooterComponent, AlertComponent } from './index';
import { ModalComponent } from './components/modal.component';


@NgModule({
  declarations: [HeaderComponent, FooterComponent, AlertComponent, ModalComponent],
  imports: [CommonModule, AuthRoutingModule],
  exports: [FooterComponent, HeaderComponent, AlertComponent],
})
export class SharedModule { }
