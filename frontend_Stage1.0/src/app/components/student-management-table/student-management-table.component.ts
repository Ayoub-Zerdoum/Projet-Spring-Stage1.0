import { Component , OnInit,OnChanges, SimpleChanges } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';



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

  selectedStatusFilter: string | null = null;
  selectedSpecialization: string | null = null;
  selectedAccountStatusFilter: string | null = null;
  minDOB: string | null = null; // Variable to hold the minimum date of birth
  maxDOB: string | null = null; // Variable to hold the maximum date of birth

  activeFilters: number = 0;
  filterChanged: boolean = false;

  showDateAlert = false;

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.students$ = this.userService.getAllStudents();
  }
  /*
  ngOnChanges(changes: SimpleChanges): void {
    // Check if any of the filter variables have changed
    if (changes['selectedStatusFilter'] || changes['selectedSpecialization'] || 
          changes['selectedAccountStatusFilter'] || changes['minDOB'] || changes['maxDOB']) {
      this.updateActiveFilters();
      this.filterChanged = true;
      console.log("filter changed :", this.filterChanged);
    }
  }*/

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
      this.students$ = this.userService.applyFilters(filters);
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
}
