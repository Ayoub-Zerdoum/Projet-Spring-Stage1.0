import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    console.log('Attempting login...');
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(
        catchError(error => {
          console.error('Login failed:', error);
          return throwError(error);
        })
      );
  }

}
