import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from "@app/shared";
import { AuthModule } from '@app/auth';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    HttpClientModule,
    AppRoutingModule,
    BrowserModule,
    SharedModule,
    AuthModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
