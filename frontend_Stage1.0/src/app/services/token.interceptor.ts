import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../services/authentication.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private authService:  AuthenticationService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();
    if (token) {
      console.log('TokenInterceptor: Adding token to request headers');
      console.log('TokenInterceptor: Token:', token);
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }else {
        console.log('TokenInterceptor: No token available');
      }
  
      console.log('TokenInterceptor: Request headers after modification:', request.headers);
    return next.handle(request);
  }
}
