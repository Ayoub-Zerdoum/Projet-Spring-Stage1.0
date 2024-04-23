import { Component, OnInit , EventEmitter, Output} from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { UserManagementService } from '../../services/user-management.service';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';



@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit{

  
  constructor(private authService: AuthenticationService,private userService: UserManagementService, private formBuilder: FormBuilder) {
    this.currentUser = this.authService.getUserInfo();
    this.userType = this.fetchUserType();
  }

  currentUser: any | null = null;
  userType : string ='';
  activeOption: string = 'User Management';

  passwordVisibility: boolean = false;
  passwordConfirmVisibility: boolean = false;
  changingPassword: boolean = false;
  passwordForm!: FormGroup;

  passwordChoisi: string = ""; // Variable to store the password value
  passwordConfirme: string = ""; // Variable to store the confirmed password value
  passwordChangeSuccess: boolean = false;


  ngOnInit() {
    this.passwordForm = this.formBuilder.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });

    const passwordControl = this.passwordForm.get('password')!;
    const confirmPasswordControl = this.passwordForm.get('confirmPassword')!;

    console.log("password init : "+passwordControl.value);
    console.log("passwordConfirm init : "+confirmPasswordControl.value);
  }

  @Output() sidebarOption: EventEmitter<string> = new EventEmitter<string>();

  resetPassword() {
    // Reset password-related variables and form fields
    this.changingPassword = false;
    this.passwordVisibility = false;
    this.passwordConfirmVisibility = false;
    this.passwordChoisi = '';
    this.passwordConfirme = '';
    this.passwordChangeSuccess = false;
  }

  confirmPassword() {
    const passwordControl = this.passwordForm.get('password')!;
    const confirmPasswordControl = this.passwordForm.get('confirmPassword')!;

    this.passwordChoisi = passwordControl.value;
    this.passwordConfirme = confirmPasswordControl.value;
    console.log("passwordChoisi : "+this.passwordChoisi);
    console.log("passwordConfirme : "+this.passwordConfirme);
    if (this.passwordChoisi === this.passwordConfirme) {
      let editService: Observable<any>;

      // Determine the edit service based on the user type
      switch (this.userType) {
        case 'STUDENT':
          editService = this.userService.editStudent(this.currentUser.userId, this.passwordForm.value);
          break;
        case 'PROFESSOR':
          editService = this.userService.editProfessor(this.currentUser.userId, this.passwordForm.value);
          break;
        case 'ADMIN':
          editService = this.userService.editAdmin(this.currentUser.userId, this.passwordForm.value);
          break;
        default:
          // Handle unsupported user type
          console.error('Unsupported user type:', this.userType);
          return;
      }
      editService.subscribe(
        response => {
          this.passwordChangeSuccess = true;
          this.changingPassword = false;
        },
        error => {
          console.error('Password change failed:', error);
        });
    } else {
      console.error('Passwords do not match');
    }
  }

  toggleVisibility() {
    this.passwordVisibility = !this.passwordVisibility;
  }

  toggleVisibility2() {
    this.passwordConfirmVisibility = !this.passwordConfirmVisibility;
  }

  changePassword(){
    this.changingPassword = true;
  }

  generateRandomPassword() {
    // Generate random password
    const randomPassword = this.authService.generateRandomString(10); // Change the length as needed
    this.passwordForm.patchValue({ 
      password: randomPassword,
      confirmPassword: randomPassword
     });
  }

  handleOptionClick(option: string) {
    this.sidebarOption.emit(option);
    this.activeOption = option;
  }

  fetchUserType(): any {
    this.authService.getUserTypeById(this.currentUser.userId).subscribe(
      (userType: string) => {
        this.userType = userType; 
        this.setInitOption(); 
      },
      (error: any) => {
        console.error('Error fetching user type:', error);
      }
    );
  }

  verifyOption(option: string): boolean {
    if (!this.currentUser) {
      return false; 
    }

    const privilege = this.currentUser.privilege;

    // Logic to determine if the option should be displayed based on userType and privilege
    switch (option) {
      case 'User Management': return this.userType === 'ADMIN' && (privilege === 'SUPER' || privilege === 'VIEW_ONLY');
      case 'Posters': return  true;
      case 'Poster Sessions': return (this.userType  ==='ADMIN' && ['SUPER','VIEW_ONLY', 'MANAGE_POSTER_SESSIONS'].includes(privilege)) || ['PROFESSOR','STUDENT'].includes(this.userType);
      case 'Demandes Stages': return (this.userType  ==='ADMIN'&& ['SUPER','VIEW_ONLY', 'MANAGE_STAGE_DEMANDS'].includes(privilege)) || this.userType === 'STUDENT';
      case 'Stage Offers': return (this.userType  ==='ADMIN'&& ['SUPER','VIEW_ONLY', 'POST_OFFERS'].includes(privilege)) || this.userType === 'STUDENT';
      default:
        return true; // Default to true if no specific logic for the option
    }
  }

  setInitOption(): void{
    const privilege = this.currentUser.privilege;
    if(this.userType === "ADMIN" && (privilege === 'SUPER' || privilege === 'VIEW_ONLY')){
      this.activeOption = 'User Management';
    }else if(this.userType === "ADMIN" && privilege === 'MANAGE_POSTER_SESSIONS'){
      this.activeOption = 'Poster Sessions';
    }else if(this.userType === "ADMIN" && privilege === 'MANAGE_STAGE_DEMANDS'){
      this.activeOption = 'Demandes Stages';
    }else if(this.userType === "ADMIN" && privilege === 'POST_OFFERS'){
      this.activeOption = 'Stage Offers';
    }else{
      this.activeOption = 'Posters';
    }

    // Emit the initial option to the parent component
    this.sidebarOption.emit(this.activeOption);
  }

  verifyTitle(title: string): boolean {
    // Implement the conditions based on the specified title
    switch (title) {
      case 'VIEW_ONLY': return this.userType === "ADMIN" && this.currentUser.privilege === 'VIEW_ONLY'; 
      case 'MANAGE_STAGE_DEMANDS': return this.userType === "ADMIN" && this.currentUser.privilege === 'MANAGE_STAGE_DEMANDS'; 
      case 'SUPER': return this.userType === "ADMIN" && this.currentUser.privilege === 'SUPER';  
      case 'MANAGE_POSTER_SESSIONS':return this.userType === "ADMIN" && this.currentUser.privilege === 'MANAGE_POSTER_SESSIONS'; 
      case 'POST_OFFERS':return this.userType === "ADMIN" && this.currentUser.privilege === 'POST_OFFERS'; 
      case 'INFORMATIQUE':return this.userType === "STUDENT" && this.currentUser.specialization === 'INFORMATIQUE';
      case 'MECATRONIQUE':return this.userType === "STUDENT" && this.currentUser.specialization === 'MECATRONIQUE';
      case 'INDUSTRIEL':return this.userType === "STUDENT" && this.currentUser.specialization === 'INDUSTRIEL';
      case 'INFOTRONIQUE':return this.userType === "STUDENT" && this.currentUser.specialization === 'INFOTRONIQUE';
      case 'INFO':return this.userType === "PROFESSOR" && this.currentUser.department === 'INFO';
      case 'ELEC':return this.userType === "PROFESSOR" && this.currentUser.department === 'ELEC';
      case 'INDUS':return this.userType === "PROFESSOR" && this.currentUser.department === 'INDUS';
      default:
        return false; 
    }
  }
  

}
