import {APP_INITIALIZER, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SharedModule } from "@app/shared";
import { AuthModule } from '@app/auth';
import { appInitializer } from '@app/core'
import { AuthService } from '@app/core/services'
import { DashboardModule } from './dashboard/dashboard.module';
import { ErrorInterceptor, JwtInterceptor } from '@app/core/interceptors';


@NgModule({
  declarations: [AppComponent],
  imports: [
    AuthModule,
    SharedModule,
    BrowserModule,
    DashboardModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],

  providers: [
    { provide: APP_INITIALIZER, useFactory: appInitializer, multi: true, deps: [AuthService] },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
