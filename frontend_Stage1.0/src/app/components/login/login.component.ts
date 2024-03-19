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
    this.authService.login(this.username, this.password).subscribe(
      response => {
        // Handle successful login
        console.log('Login successful');
        // Navigate to the desired route (e.g., '/admin')
        this.router.navigate(['/admin']);
      },
      error => {
        // Handle login error
        console.error('Invalid username or password');
        this.isValid = false;
      }
    );
  }
}
