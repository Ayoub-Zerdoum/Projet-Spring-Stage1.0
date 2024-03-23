import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';


import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-professor-management-table',
  templateUrl: './professor-management-table.component.html',
  styleUrls: ['./professor-management-table.component.css']
})
export class ProfessorManagementTableComponent implements OnInit{
  profs$!: Observable<any[]>;
  searchOption: string = 'Username';
  searchInput = new FormControl('');

  selectedDepartmentFilter: string | null = null;
  selectedAccountStatusFilter: string | null = null;

  activeFilters: number = 0;
  filterChanged: boolean = false;

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.profs$ = this.userService.getAllProfs();
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
}
