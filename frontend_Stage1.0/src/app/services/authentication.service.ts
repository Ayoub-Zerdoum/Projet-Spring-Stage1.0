import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor() { }
  login(username: string, password: string): boolean {
    return true; // Placeholder value
  }
}
