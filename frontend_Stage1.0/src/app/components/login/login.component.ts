import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';
  isValid: boolean = false;
  formSubmitted: boolean = false;

  constructor(private authService: AuthenticationService,private router: Router) {}

  onSubmit() {
    this.login();
    this.formSubmitted = true;
  } 

  login() {
    const fixedUsername = 'user';
    const fixedPassword = 'password';

    // Check if the entered username and password match the fixed values
    if (this.username === fixedUsername && this.password === fixedPassword) {
      // Simulating a successful login
      console.log('Login successful');
      this.router.navigate(['/admin']);
    } else {
      // Simulating a login error
      console.error('Invalid username or password');
      this.isValid = false;
    }
  }
}
