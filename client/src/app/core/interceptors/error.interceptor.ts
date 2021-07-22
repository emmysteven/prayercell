import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators'
import { AlertService, AuthService } from '@app/core/services'

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private alertService: AlertService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(catchError(err => {
      if([401, 403].includes(err.status) && this.authService.userValue){
        //auto logout if 401 or 403 response return from api
        this.authService.logout();
      }

      const error = (err && err.error && err.error.message)
      this.alertService.error(error);
      console.log(err.error.message);
      return throwError(error);
    }));
  }
}
