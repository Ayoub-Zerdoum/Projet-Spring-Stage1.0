import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class UserManagementService {
  private baseUrl = environment.apiUrl; // Assuming your backend APIs are served from this base URL

  constructor(private http: HttpClient) { }

  getAllAdmins(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admins/all`);
  }

  getAllProfs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/professors/all`);
  }

  getAllStudents(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students/all`);
  }

  searchStudentsByUsername(usernameQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students/search-username?username=${usernameQuery}`);
  }

  searchStudentsByEmail(emailQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students/search-email?email=${emailQuery}`);
  }

  searchStudentsByTelephone(telephoneQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/students/search-telephone?telephone=${telephoneQuery}`);
  }

  searchAdminsByUsername(usernameQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admins/search-username?username=${usernameQuery}`);
  }

  searchAdminsByEmail(emailQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admins/search-email?email=${emailQuery}`);
  }

  searchAdminsByTelephone(telephoneQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admins/search-telephone?telephone=${telephoneQuery}`);
  }

  searchProfessorsByUsername(usernameQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/professors/search-username?username=${usernameQuery}`);
  }

  searchProfessorsByEmail(emailQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/professors/search-email?email=${emailQuery}`);
  }

  searchProfessorsByTelephone(telephoneQuery: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/professors/search-telephone?telephone=${telephoneQuery}`);
  }

  addAdmin(admin: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/admins/add`, admin);
  }

  
}
