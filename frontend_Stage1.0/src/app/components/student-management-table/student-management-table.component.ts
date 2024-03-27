import { Component , OnInit,OnChanges, SimpleChanges } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl ,FormBuilder, FormGroup, Validators} from '@angular/forms';



import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-student-management-table',
  templateUrl: './student-management-table.component.html',
  styleUrls: ['./student-management-table.component.css']
})
export class StudentManagementTableComponent implements OnInit{
  students$!: Observable<any[]>;
  searchOption: string = 'Username';
  searchInput = new FormControl('');

  newUserForm!: FormGroup;

  selectedStatusFilter: string | null = null;
  selectedSpecialization: string | null = null;
  selectedAccountStatusFilter: string | null = null;
  minDOB: string | null = null; // Variable to hold the minimum date of birth
  maxDOB: string | null = null; // Variable to hold the maximum date of birth

  activeFilters: number = 0;
  filterChanged: boolean = false;

  showDateAlert = false;

  addingActive: boolean = false; // Variable to indicate if adding new student is active
  selectedNewStudentSpecialization: string | null = null; // Variable to store the selected specialization for the new student

  incompleteNewUserSubmit = false;
  NewUserCreatedSuccesfully = false;
  NewUserCreatedError = false;
  invalidNewUsername = false;
  invalidNewUserEmail = false;
  invalidNewUserTelephone = false;


  studentToDelete: any | null = null;;
  UserDeleteConfirmed: boolean = false;
  DeleteIsPossible: boolean = false;
  UserDeletedSuccessfully: boolean = false;
  UserDeleteConcelled: boolean = false;

  constructor(private userService: UserManagementService,private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.students$ = this.userService.getAllStudents();

    this.newUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      dateOfBirth: ['', Validators.required],
      enrolled: [true],
      sendVerificationEmail: [true]
    });
  }

  
  getStudentById(studentId: number): any {
    this.userService.getStudentById(studentId)
      .subscribe(
        (data: any) => {
          console.log('Student details:', data);
          return data;
        },
        error => {
          console.error('Error fetching student:', error);
          return null;
        }
      );
  }
  
  

  updateSearchOption(option: string) {
    this.searchOption = option;
    this.searchStudents(this.searchInput.value!.trim(), option);
  }

  triggerSearch() {
    this.searchStudents(this.searchInput.value!.trim(), this.searchOption);

    this.selectedStatusFilter = null;
    this.selectedSpecialization = null;
    this.selectedAccountStatusFilter = null;
    this.minDOB = null;
    this.maxDOB = null;
    this.filterChanged = false;
    this.activeFilters = 0;
  }
  
  searchStudents(searchQuery: string, option: string) {
    console.log('Searching students with query:', searchQuery);
    if (searchQuery) {
      switch(option) {
        case 'Username':
          this.students$ = this.userService.searchStudentsByUsername(searchQuery);
          break;
        case 'Email':
          this.students$ = this.userService.searchStudentsByEmail(searchQuery);
          break;
        case 'Telephone':
          this.students$ = this.userService.searchStudentsByTelephone(searchQuery);
          break;
        case 'DateOfBirth':
          this.students$ = this.userService.searchStudentsByDateOfBirth(searchQuery);
          break;
        default:
          this.students$ = this.userService.getAllStudents();
          break;
      }
    } else {
      this.students$ = this.userService.getAllStudents(); // Reset to all students if search query is empty
    }
  }

  toggleStatusFilter(filter: string) {
    if (this.selectedStatusFilter === filter) {
      // If the clicked filter is already selected, deselect it
      this.selectedStatusFilter = null;
    } else {
      // Otherwise, select the clicked filter
      this.selectedStatusFilter = filter;
    }
    this.updateActiveFilters();
  }

  toggleSpecialization(specialization: string){
    if (this.selectedSpecialization === specialization) {
      this.selectedSpecialization = null; // Uncheck if it's already selected
    } else {
      this.selectedSpecialization = specialization; // Check if it's not selected
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

  resetDateFilters() {
    this.minDOB = null;
    this.maxDOB = null;
    this.updateActiveFilters();
    // Call your filtering function here to update the data based on the reset filters
  }

  applyFilters(): void {
    const filters: any  = {
      studentStatus: this.selectedStatusFilter || null,
      specialization: this.selectedSpecialization || null,
      accountStatus: this.selectedAccountStatusFilter || null,
      dobMin: this.minDOB || null,
      dobMax: this.maxDOB || null
    };

    
    // Remove null parameters from the object
    Object.keys(filters).forEach(key => filters[key] === null && delete filters[key]);

  
    // Call the service method to apply the filters
    if (this.checkDateRange()) {
      this.searchInput.setValue('');
      this.filterChanged = false;
      this.students$ = this.userService.filterStudents(filters);
    }
  }

  updateActiveFilters(): void {
    // Reset count to 0 before counting active filters
    this.activeFilters = 0;
    this.filterChanged = true;

    if (this.selectedStatusFilter !== null) {
        this.activeFilters++;
    }
    if (this.selectedSpecialization !== null) {
        this.activeFilters++;
    }
    if (this.selectedAccountStatusFilter !== null) {
        this.activeFilters++;
    }
    if (this.minDOB !== null) {
        this.activeFilters++;
    }
    if (this.maxDOB !== null) {
        this.activeFilters++;
    }
  }

  checkDateRange(): boolean {
    // Parse the date strings to Date objects
    const minDate = this.minDOB ? new Date(this.minDOB) : null;
    const maxDate = this.maxDOB ? new Date(this.maxDOB) : null;

    // Check if minDate is after maxDate
    if (minDate && maxDate && minDate > maxDate) {
      this.showDateAlert = true;
      return false;
    }else{
      this.showDateAlert = false;
      return true;
    }
    
  }

  selectNewStudentSpecialization(specialization: string): void {
    this.selectedNewStudentSpecialization = specialization;
  }

  toggleAddingActive(): void {
    this.addingActive = !this.addingActive;
  }

  createUser(): void {
    if (this.isFormComplete()) {
      const formData = { ...this.newUserForm.value,
                         specialization: this.selectedNewStudentSpecialization,
                         accountStatus: 'ACTIVE' };
      this.userService.createStudentViaForm(formData).subscribe(
        response => {
          console.log('Student added successfully:', response);
          this.NewUserCreatedSuccesfully = true;
          this.NewUserCreatedError = false;
          this.incompleteNewUserSubmit = false;
        },
        error => {
          console.error('Error adding student:', error);
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
      enrolled: true,
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
    return this.newUserForm.valid && !!this.selectedNewStudentSpecialization;
  }

  /*DELETE STUDENTS */
  DeleteThisStudent(student: any): void {
    this.studentToDelete = student;
    this.UserDeleteConfirmed = false;
    // Check if deletion is possible
    if(student.requestStages.length === 0 && student.offerApplications.length === 0){
      this.DeleteIsPossible = true;
    } else {
      this.DeleteIsPossible = false;
    }
  }

  // Method to confirm the deletion
confirmDelete(): void {
  if (this.DeleteIsPossible) {
      // Delete student
      this.userService.deleteStudent(this.studentToDelete.userId).subscribe(
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
      this.suspendAccount(this.studentToDelete.userId);
      this.UserDeleteConfirmed = true;
  }
}

  // Method to suspend the account
  suspendAccount(studentId: number): void {
    // Call backend API to suspend the account
    this.userService.suspendAccount(studentId).subscribe(
        () => {
            console.log('Account suspended successfully');
            this.UserDeletedSuccessfully = true;
        },
        error => {
            console.error('Error suspending account:', error);
        }
    );
  }

  resetDeleteParams(): void {
    this.studentToDelete = null;
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
}
