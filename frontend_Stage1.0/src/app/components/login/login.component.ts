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
  email = '';
  isValid: boolean = false;
  formSubmitted: boolean = false;
  PasswordVisibility: boolean = false;

  LoginActive: boolean = true;
  validatingPassword:boolean = false;
  private verificationCode: string ='';
  // Define variables to represent the status
  private readonly STATUS_SUCCESS = 'success';
  private readonly STATUS_FAIL = 'fail';

  

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

  ForgotenPasswordStart(){
    this.LoginActive = false;
  }

  generateVerificationCode(): void {
    // Generate a random verification code
    this.verificationCode = Math.floor(Math.random() * 90000) + 10000 + ''; // Generate a 6-digit code
  }

  getVerificationCode(): string {
    return this.verificationCode;
  }

  sendVerificationEmail(): void {
    // Generate verification code
    this.generateVerificationCode();

    // Send verification email with the generated code
    this.authService.sendPasswordVerificationEmail(this.email, this.verificationCode).subscribe(
      (response: any) => {
        console.log('Response:', response);
        this.validatingPassword = true;
      },
      (error) => {
        console.error('Error sending verification email:', error);
        // Handle error
      }
    );
  }

  // Check for status variables
  isVerificationEmailSent(status: string): boolean {
    return status === this.STATUS_SUCCESS;
  }

  isVerificationEmailFailed(status: string): boolean {
    return status === this.STATUS_FAIL;
  }
}
