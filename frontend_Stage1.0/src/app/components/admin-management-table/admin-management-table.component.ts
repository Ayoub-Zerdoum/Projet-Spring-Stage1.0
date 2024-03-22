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
}
