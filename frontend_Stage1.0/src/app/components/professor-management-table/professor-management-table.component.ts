import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl ,FormBuilder, FormGroup, Validators} from '@angular/forms';


import { UserManagementService } from '../../services/user-management.service';
import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'app-professor-management-table',
  templateUrl: './professor-management-table.component.html',
  styleUrls: ['./professor-management-table.component.css']
})
export class ProfessorManagementTableComponent implements OnInit{
  currentUser: any | null = null;

  profs$!: Observable<any[]>;
  searchOption: string = 'Username';
  searchInput = new FormControl('');

  selectedDepartmentFilter: string | null = null;
  selectedAccountStatusFilter: string | null = null;

  activeFilters: number = 0;
  filterChanged: boolean = false;
  PasswordVisibility: boolean = false;


  addingActive: boolean = false; // Variable to indicate if adding new student is active
  selectedNewProfessorDepartment: string | null = null; // Variable to store the selected specialization for the new student

  newUserForm!: FormGroup;
  incompleteNewUserSubmit = false;
  NewUserCreatedSuccesfully = false;
  NewUserCreatedError = false;
  invalidNewUsername = false;
  invalidNewUserEmail = false;
  invalidNewUserTelephone = false;

  professorToDelete: any | null = null;;
  UserDeleteConfirmed: boolean = false;
  DeleteIsPossible: boolean = false;
  UserDeletedSuccessfully: boolean = false;
  UserDeleteConcelled: boolean = false;

  professorToView: any | null = null;

/* Edit variables for professors */
selectedEditDepartment: string = 'INFO'; // Default department
professorToEdit: any;
editProfessorForm!: FormGroup;
incompleteEditUserSubmit = false;
UserEditedSuccessfully = false;
UserEditError = false;
invalidEditUsername = false;
invalidEditUserEmail = false;
invalidEditUserTelephone = false;

  constructor(private authService: AuthenticationService,private userService: UserManagementService,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.profs$ = this.userService.getAllProfs();
    this.currentUser = this.authService.getUserInfo();

    this.newUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      sendVerificationEmail: [true]
    });

    // Initialize the edit professor form
    this.editProfessorForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: [''],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.pattern('[0-9]*'), Validators.minLength(8), Validators.maxLength(8)]],
      department: this.selectedEditDepartment // Assuming department is part of the edit form
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
    this.searchProfs(this.searchInput.value!.trim(), option);
  }

  triggerSearch() {
    this.searchProfs(this.searchInput.value!.trim(), this.searchOption);
  }
  
  searchProfs(searchQuery: string, option: string) {
    console.log('Searching profs with query:', searchQuery);
    if (searchQuery) {
      switch(option) {
        case 'Username':
          this.profs$ = this.userService.searchProfessorsByUsername(searchQuery);
          break;
        case 'Email':
          this.profs$ = this.userService.searchProfessorsByEmail(searchQuery);
          break;
        case 'Telephone':
          this.profs$ = this.userService.searchProfessorsByTelephone(searchQuery);
          break;
        default:
          this.profs$ = this.userService.getAllProfs();
          break;
      }
    } else {
      this.profs$ = this.userService.getAllStudents(); // Reset to all students if search query is empty
    }
  }

  applyFilters(): void {
    // Create an object with filter values
    const filters: any = {
      Departement: this.selectedDepartmentFilter || null,
      accountStatus: this.selectedAccountStatusFilter || null
    };

    this.filterChanged = false;

    // Remove null parameters from the object
    Object.keys(filters).forEach(key => filters[key] === null && delete filters[key]);

    // Call the service method to apply the filters
    this.profs$ = this.userService.filterProfessors(filters);
  }

  updateActiveFilters(): void {
    // Reset count to 0 before counting active filters
    this.activeFilters = 0;
    this.filterChanged = true;

    if (this.selectedDepartmentFilter !== null) {
        this.activeFilters++;
    }
    if (this.selectedAccountStatusFilter !== null) {
        this.activeFilters++;
    }
  }

  toggleDepartmentFilter(department: string): void {
    if (this.selectedDepartmentFilter === department) {
      this.selectedDepartmentFilter = null; // Deselect if already selected
    } else {
      this.selectedDepartmentFilter = department; // Otherwise, select the status
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

  selectNewProfessorDepartment(Department: string): void {
    this.selectedNewProfessorDepartment = Department;
  }

  toggleAddingActive(): void {
    this.addingActive = !this.addingActive;
  }

  createUser(): void {
    if (this.isFormComplete()) {
      const formData = { ...this.newUserForm.value,
                         department: this.selectedNewProfessorDepartment,
                         accountStatus: 'ACTIVE' };
      this.userService.createProfessorViaForm(formData).subscribe(
        response => {
          console.log('Professor added successfully:', response);
          this.NewUserCreatedSuccesfully = true;
          this.NewUserCreatedError = false;
          this.incompleteNewUserSubmit = false;
        },
        error => {
          console.error('Error adding professor:', error);
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
    return this.newUserForm.valid && !!this.selectedNewProfessorDepartment;
  }

  /*DELETE STUDENTS */
  DeleteThisProfessor(prof: any): void {
    this.professorToDelete = prof;
    this.UserDeleteConfirmed = false;
    // Check if deletion is possible
    if(prof.sessionPosters.length === 0){
      this.DeleteIsPossible = true;
    } else {
      this.DeleteIsPossible = false;
    }
  }

  // Method to confirm the deletion
confirmDelete(): void {
  if (this.DeleteIsPossible) {
      // Delete student
      this.userService.deleteProfessor(this.professorToDelete.userId).subscribe(
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
      this.suspendAccount(this.professorToDelete.userId);
      this.UserDeleteConfirmed = true;
  }
}

  // Method to suspend the account
  suspendAccount(prof: any): void {
    // Call backend API to suspend the account
    this.userService.suspendProfAccount(prof.userId).subscribe(
        () => {
            console.log('Account suspended successfully');
            this.UserDeletedSuccessfully = true;
            prof.accountStatus = "SUSPENDED";
        },
        error => {
            console.error('Error suspending account:', error);
        }
    );
  }

// Method to activate the account
activateAccount(prof: any): void {
  // Call backend API to activate the account
  this.userService.activateProfAccount(prof.userId).subscribe(
    () => {
      console.log('Account activated successfully');
      prof.accountStatus = "ACTIVE";
    },
    error => {
      console.error('Error activating account:', error);
      // Optionally, handle the error by displaying an error message or performing other actions
    }
  );
}

  resetDeleteParams(): void {
    this.professorToDelete = null;
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

  /*Editing */
  // Method to reset the edit user form
  resetEditUserForm(): void {
    this.editProfessorForm.reset();
  }
  // Method to handle editing professor
editProfessor(): void {
  
  if (this.editProfessorForm.invalid) {
    this.incompleteEditUserSubmit = true;
    return;
  }

  this.editProfessorForm.patchValue({
    department: this.selectedEditDepartment
  });

  // Call the service method to edit the professor
  this.userService.editProfessor(this.professorToEdit.userId, this.editProfessorForm.value).subscribe(
    () => {
      console.log('Professor edited successfully');
      this.UserEditedSuccessfully = true;
      // Optionally, you can perform additional actions after successful edit
    },
    error => {
      console.error('Error editing professor:', error);
      this.UserEditError = true;
      // Optionally, handle the error by displaying an error message or performing other actions
    }
  );
}

// Method to set the selected professor for editing
setProfessorToEdit(professor: any): void {
  this.professorToEdit = professor;
  this.selectedEditDepartment = this.professorToEdit.department;
  // Populate the form fields with the selected professor's data
  this.editProfessorForm.patchValue({
    username: professor.username,
    email: professor.email,
    telephone: professor.telephone,
    password: '',
    department: professor.department // Assuming department is part of the edit form
  });

  this.incompleteEditUserSubmit = false;
  this.UserEditedSuccessfully = false;
  this.UserEditError = false;
  this.invalidEditUsername = false;
  this.invalidEditUserEmail = false;
  this.invalidEditUserTelephone = false;
}
}
