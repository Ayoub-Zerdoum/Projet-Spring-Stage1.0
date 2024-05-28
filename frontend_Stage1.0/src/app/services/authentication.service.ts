import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

import { Observable } from 'rxjs';


import { catchError ,tap,map} from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    console.log('Attempting login...');
    localStorage.removeItem('token');
    localStorage.removeItem('currentUser');
    return this.http.post<any>(`${environment.apiUrl}/auth/login`, { username, password })
      .pipe(
        tap(response => {
          // Store user information in local storage
          localStorage.setItem('token', response.token);
          localStorage.setItem('currentUser', JSON.stringify(response.user));
        }),
        catchError(error => {
          console.error('Login failed:', error);
          return throwError(error);
        })
      );
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUser(): any {
    const userString = localStorage.getItem('user');
    return userString ? JSON.parse(userString) : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout() {
    // Clear the token and user information from local storage
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  getUserTypeById(userId: number): Observable<string> {
    return this.http.get<{ userType: string }>(`${environment.apiUrl}/auth/user/${userId}/type`)
      .pipe(
        map((response: { userType: string }) => response.userType)
      );
  }

  getUserInfo() {
    const currentUser = localStorage.getItem('currentUser');
    if (currentUser !== null) {
      return JSON.parse(currentUser);
    } else {
      return null; // Or any other appropriate default value
    }
  }

  generateRandomString(length: number): string {
    let result = '';
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    const charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }

  sendPasswordVerificationEmail(email: string, code: string): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/user/sendPasswordVerificationEmail`, { email, code });
  }

}
