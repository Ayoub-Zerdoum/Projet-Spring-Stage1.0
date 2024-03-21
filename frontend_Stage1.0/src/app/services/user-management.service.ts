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

  addAdmin(admin: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/admins/add`, admin);
  }

  
}
