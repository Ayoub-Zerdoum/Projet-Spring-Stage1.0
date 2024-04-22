import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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

  searchStudentsByDateOfBirth(dateOfBirthQuery: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/students/search-date-of-birth?dateOfBirth=${dateOfBirthQuery}`);
  }

  filterStudents(filters: any): Observable<any> {
    let params = new HttpParams();
    Object.keys(filters).forEach(key => {
      if (filters[key] !== undefined) {
        params = params.set(key, filters[key]);
      }
    });
    return this.http.get(`${this.baseUrl}/students/filter`, { params });
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

  filterAdmins(filters: any): Observable<any> {
    let params = new HttpParams();
    if (filters.privilege) {
      params = params.set('privilege', filters.privilege);
    }
    if (filters.accountStatus) {
      params = params.set('accountStatus', filters.accountStatus);
    }
    return this.http.get<any>(`${this.baseUrl}/admins/filter`, { params });
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

  filterProfessors(filters: any): Observable<any> {
    let params = new HttpParams();
    if (filters.department) {
      params = params.set('department', filters.department);
    }
    if (filters.accountStatus) {
      params = params.set('accountStatus', filters.accountStatus);
    }
    return this.http.get<any>(`${this.baseUrl}/professors/filter`, { params });
  }

  addAdmin(admin: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/admins/add`, admin);
  }

  createStudentViaForm(stdFormData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/students/addv2`, stdFormData);
  }

  createAdminViaForm(adFormData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/admins/addv2`, adFormData);
  }

  createProfessorViaForm(profFormData: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/professors/addv2`, profFormData);
  }

  /*DELETE */
  deleteStudent(studentId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/students/delete/${studentId}`);
  }

  deleteAdmin(adminId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/admins/delete/${adminId}`);
  }

  deleteProfessor(profId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/professors/delete/${profId}`);
  }

  /*GET*/
  getStudentById(studentId: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/students/${studentId}`);
  }

  /*changing states*/
  suspendStudentAccount(studentId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/students/suspend/${studentId}`, {});
  }

  suspendProfAccount(profId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/professors/suspend/${profId}`, {});
  }

  suspendAdminAccount(adminId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/admins/suspend/${adminId}`, {});
  }

  activateStudentAccount(studentId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/students/activate/${studentId}`, {});
  }

  activateProfAccount(profId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/professors/activate/${profId}`, {});
  }

  activateAdminAccount(adminId: number): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/admins/activate/${adminId}`, {});
  }

  /*Edit */
  // Method to edit user
  editStudent(studentId: number ,studentData: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/students/edit/${studentId}`, studentData);
  }

  editProfessor(profId: number ,profData: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/professors/edit/${profId}`, profData);
  }

  editAdmin(adminId: number ,adminData: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/admins/edit/${adminId}`, adminData);
  }

}
