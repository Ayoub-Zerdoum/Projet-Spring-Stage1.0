import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';


import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-admin-management-table',
  templateUrl: './admin-management-table.component.html',
  styleUrls: ['./admin-management-table.component.css']
})
export class AdminManagementTableComponent implements OnInit{
  admins$!: Observable<any[]>;
  searchOption: string = 'Username';
  searchInput = new FormControl('');
  selectedPrivilegeFilter: string | null = null;
  selectedAccountStatusFilter: string | null = null;

  activeFilters: number = 0;
  filterChanged: boolean = false;

  addingActive: boolean = false; // Variable to indicate if adding new student is active
  selectedNewAdminPrivilege: string | null = null; // Variable to store the selected specialization for the new student

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.admins$ = this.userService.getAllAdmins();
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

}
