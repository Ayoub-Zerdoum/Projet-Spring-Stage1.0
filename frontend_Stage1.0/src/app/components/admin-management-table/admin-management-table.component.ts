import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl ,FormBuilder, FormGroup, Validators} from '@angular/forms';


import { UserManagementService } from '../../services/user-management.service';
import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'app-admin-management-table',
  templateUrl: './admin-management-table.component.html',
  styleUrls: ['./admin-management-table.component.css']
})
export class AdminManagementTableComponent implements OnInit{
  currentUser: any | null = null;

  admins$!: Observable<any[]>;
  searchOption: string = 'Username';
  searchInput = new FormControl('');
  selectedPrivilegeFilter: string | null = null;
  selectedAccountStatusFilter: string | null = null;

  activeFilters: number = 0;
  filterChanged: boolean = false;

  PasswordVisibility: boolean = false;


  addingActive: boolean = false; // Variable to indicate if adding new student is active
  selectedNewAdminPrivilege: string | null = null; // Variable to store the selected specialization for the new student

  newUserForm!: FormGroup;
  incompleteNewUserSubmit = false;
  NewUserCreatedSuccesfully = false;
  NewUserCreatedError = false;
  invalidNewUsername = false;
  invalidNewUserEmail = false;
  invalidNewUserTelephone = false;

  adminToDelete: any | null = null;
  UserDeleteConfirmed: boolean = false;
  DeleteIsPossible: boolean = false;
  UserDeletedSuccessfully: boolean = false;
  UserDeleteConcelled: boolean = false;

  adminToView: any | null = null;

  /* Edit variables for admins */
  selectedEditPrivilege: string = 'SUPER'; // Default privilege
  adminToEdit: any;
  editUserForm!: FormGroup;
  incompleteEditUserSubmit = false;
  UserEditedSuccessfully = false;
  UserEditError = false;
  invalidEditUsername = false;
  invalidEditUserEmail = false;
  invalidEditUserTelephone = false;

  constructor(private authService: AuthenticationService,private userService: UserManagementService,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.admins$ = this.userService.getAllAdmins();
    this.currentUser = this.authService.getUserInfo();

    this.newUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      sendVerificationEmail: [true]
    });

    // Initialize the edit admin form
    this.editUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: [''],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(8), Validators.maxLength(8)]],
      privilege: this.selectedEditPrivilege 
    });
  }

  toggleVisibility() {
    this.PasswordVisibility = !this.PasswordVisibility;
  }

  generateRandomPassword() {
    // Generate random password
    const randomPassword = this.authService.generateRandomString(10); // Change the length as needed
    this.newUserForm.patchValue({ 
      password: randomPassword
     });
  }


  updateSearchOption(option: string) {
    this.searchOption = option;
    this.searchadmins(this.searchInput.value!.trim(), option);
  }

  triggerSearch() {
    this.searchadmins(this.searchInput.value!.trim(), this.searchOption);
  }
  
  searchadmins(searchQuery: string, option: string) {
    console.log('Searching admins with query:', searchQuery);
    if (searchQuery) {
      switch(option) {
        case 'Username':
          this.admins$ = this.userService.searchAdminsByUsername(searchQuery);
          break;
        case 'Email':
          this.admins$ = this.userService.searchAdminsByEmail(searchQuery);
          break;
        case 'Telephone':
          this.admins$ = this.userService.searchAdminsByTelephone(searchQuery);
          break;
        default:
          this.admins$ = this.userService.getAllAdmins();
          break;
      }
    } else {
      this.admins$ = this.userService.getAllAdmins(); // Reset to all admins if search query is empty
    }
  }

  applyFilters(): void {
    // Create an object with filter values
    const filters: any = {
      privilege: this.selectedPrivilegeFilter || null,
      accountStatus: this.selectedAccountStatusFilter || null
    };

    this.filterChanged = false;

    // Remove null parameters from the object
    Object.keys(filters).forEach(key => filters[key] === null && delete filters[key]);

    // Call the service method to apply the filters
    this.admins$ = this.userService.filterAdmins(filters);
  }

  updateActiveFilters(): void {
    // Reset count to 0 before counting active filters
    this.activeFilters = 0;
    this.filterChanged = true;

    if (this.selectedPrivilegeFilter !== null) {
        this.activeFilters++;
    }
    if (this.selectedAccountStatusFilter !== null) {
        this.activeFilters++;
    }
  }

  togglePrivilegeFilter(privilege: string): void {
    if (this.selectedPrivilegeFilter === privilege) {
      this.selectedPrivilegeFilter = null; // Deselect if already selected
    } else {
      this.selectedPrivilegeFilter = privilege; // Otherwise, select the status
    }
    this.updateActiveFilters();
  }

  toggleAccountStatusFilter(status: string): void {
    if (this.selectedAccountStatusFilter === status) {
      this.selectedAccountStatusFilter = null; // Deselect if already selected
    } else {
      this.selectedAccountStatusFilter = status; // Otherwise, select the status
    }
    this.updateActiveFilters();
  }

  selectNewAdminPrivilege(privilege: string): void {
    this.selectedNewAdminPrivilege = privilege;
  }

  toggleAddingActive(): void {
    this.addingActive = !this.addingActive;
  }

  createUser(): void {
    if (this.isFormComplete()) {
      const formData = { ...this.newUserForm.value,
                         privilege: this.selectedNewAdminPrivilege,
                         accountStatus: 'ACTIVE' };
      this.userService.createAdminViaForm(formData).subscribe(
        response => {
          console.log('admin added successfully:', response);
          this.NewUserCreatedSuccesfully = true;
          this.NewUserCreatedError = false;
          this.incompleteNewUserSubmit = false;
        },
        error => {
          console.error('Error adding admin:', error);
          this.NewUserCreatedError = true;
          this.NewUserCreatedSuccesfully = false;

        
        }
      );
    } else {
      // Set flag to show incomplete form alert
      this.incompleteNewUserSubmit = true;
      this.NewUserCreatedSuccesfully = false; // Reset flag to prevent showing success alert
      this.NewUserCreatedError = false;
    }
  }
  
  resetNewUserForm(): void {
    this.newUserForm.reset(); // Reset the form
    this.newUserForm.reset({
      sendVerificationEmail: true
    });
    
    this.incompleteNewUserSubmit = false;
    this.invalidNewUsername = false;
    this.invalidNewUserEmail = false;
    this.invalidNewUserTelephone = false;
    this.NewUserCreatedSuccesfully = false; // Reset the flag to indicate form is not submitted
    this.NewUserCreatedError = false;
  }

  isFormComplete(): boolean {
    return this.newUserForm.valid && !!this.selectedNewAdminPrivilege;
  }

  /*DELETE STUDENTS */
  DeleteThisAdmin(admin: any): void {
    this.adminToDelete = admin;
    this.UserDeleteConfirmed = false;
    // Check if deletion is possible
    if(admin.responses.length === 0){
      this.DeleteIsPossible = true;
    } else {
      this.DeleteIsPossible = false;
    }
  }

  // Method to confirm the deletion
confirmDelete(): void {
  if (this.DeleteIsPossible) {
      // Delete student
      this.userService.deleteAdmin(this.adminToDelete.userId).subscribe(
        () => {
            console.log('student deleted successfully');
            this.UserDeletedSuccessfully = true;
            this.UserDeleteConfirmed = true;
        },
        error => {
            console.error('Error suspending account:', error);
        }
    );;
  } else {
      // Suspend account
      this.suspendAccount(this.adminToDelete.userId);
      this.UserDeleteConfirmed = true;
  }
}

  // Method to suspend the account
  suspendAccount(admin: any): void {
    // Call backend API to suspend the account
    this.userService.suspendAdminAccount(admin.userId).subscribe(
        () => {
            console.log('Account suspended successfully');
            this.UserDeletedSuccessfully = true;
            admin.accountStatus = "SUSPENDED";
        },
        error => {
            console.error('Error suspending account:', error);
        }
    );
  }

  // Method to activate the account
activateAccount(admin: any): void {
  // Call backend API to activate the account
  this.userService.activateAdminAccount(admin.userId).subscribe(
    () => {
      console.log('Account activated successfully');
      admin.accountStatus = "ACTIVE";
    },
    error => {
      console.error('Error activating account:', error);
      // Optionally, handle the error by displaying an error message or performing other actions
    }
  );
}

  resetDeleteParams(): void {
    this.adminToDelete = null;
    this.UserDeleteConfirmed = false;
    this.DeleteIsPossible = false;
    this.UserDeletedSuccessfully = false;
    this.UserDeleteConcelled = false;
  }
  
  abandonDeleting(): void {
    this.resetDeleteParams();
  }

  cancelDeleting(): void {
    this.resetDeleteParams();
    this.UserDeleteConcelled = true;
  }

  // Method to handle editing admin
  // Method to reset the edit user form
  resetEditUserForm(): void {
    this.editUserForm.reset();
  }

  editAdmin(): void {
    if (this.editUserForm.invalid) {
      this.incompleteEditUserSubmit = true;
      return;
    }

    this.editUserForm.patchValue({
      privilege: this.selectedEditPrivilege
    });

    // Call the service method to edit the admin
    this.userService.editAdmin(this.adminToEdit.userId, this.editUserForm.value).subscribe(
      () => {
        console.log('Admin edited successfully');
        this.UserEditedSuccessfully = true;
        // Optionally, you can perform additional actions after successful edit
      },
      error => {
        console.error('Error editing admin:', error);
        this.UserEditError = true;
        // Optionally, handle the error by displaying an error message or performing other actions
      }
    );
  }

  // Method to set the selected admin for editing
  setAdminToEdit(admin: any): void {
    this.adminToEdit = admin;
    this.selectedEditPrivilege = this.adminToEdit.privilege;
    // Populate the form fields with the selected admin's data
    this.editUserForm.patchValue({
      username: admin.username,
      email: admin.email,
      telephone: admin.telephone,
      password: '',
      privilege: admin.privilege // Assuming privilege is part of the edit form
    });

    this.incompleteEditUserSubmit = false;
    this.UserEditedSuccessfully = false;
    this.UserEditError = false;
    this.invalidEditUsername = false;
    this.invalidEditUserEmail = false;
    this.invalidEditUserTelephone = false;
  }

}
