import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  currentUser: any | null = null;
  userType : string ='';
  username = '';
  password = '';
  isValid: boolean = false;
  formSubmitted: boolean = false;
  PasswordVisibility: boolean = false;

  

  constructor(private authService: AuthenticationService,private router: Router) {}

  onSubmit() {
    this.login();
    this.formSubmitted = true;
  } 

  toggleVisibility() {
    this.PasswordVisibility = !this.PasswordVisibility;
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        // Handle successful login
        console.log('Login successful');
        this.isValid = true;
        this.currentUser = this.authService.getUserInfo();
        this.userType = this.fetchUserType();
      },
      error => {
        // Handle login error
        console.error('Invalid username or password');
        this.isValid = false;
      }
    );
  }

  fetchUserType(): any {
    this.authService.getUserTypeById(this.currentUser.userId).subscribe(
      (userType: string) => {
        console.log("user type obtained");
        console.log("user type: " ,userType);
        this.userType = userType;
        this.redirectUser(this.userType);
      },
      (error: any) => {
        console.log("user not type obtained !!!");
        console.error('Error fetching user type:', error);
      }
    );
  }

  private redirectUser(usertype: any) {
    console.log("user type in redrecting:" ,usertype);
    if (usertype === 'ADMIN') {
      console.log("starting navigation to admin dashboard");
      this.router.navigate(['/admin']);
    } else if (usertype === 'PROFESSOR') {
      this.router.navigate(['/login']);
    } else if (usertype === 'STUDENT') {
      this.router.navigate(['/admin']);
    } else {
      this.router.navigate(['/admin']);
    }
  }
}
