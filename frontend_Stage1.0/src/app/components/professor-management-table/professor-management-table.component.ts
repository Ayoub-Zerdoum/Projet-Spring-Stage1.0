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
}
