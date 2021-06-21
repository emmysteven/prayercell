import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthService} from '@app/core/services'
import { environment } from "@env/environment";

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  private baseUrl: string = environment.baseUrl;
  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    //add auth header with jwt if user is logged in & request is to the api url
    const user = this.authService.userValue;
    const isLoggedIn = user && user.accessToken;
    const isApiUrl = request.url.startsWith(this.baseUrl)
    if (isLoggedIn && isApiUrl) {
      request = request.clone({
        setHeaders: { Authorization: `Bearer ${user.accessToken}` }
      });
    }
    return next.handle(request);
  }
}
