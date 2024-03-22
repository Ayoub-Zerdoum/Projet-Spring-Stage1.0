import { Component , OnInit} from '@angular/core';
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


  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.students$ = this.userService.getAllStudents();
  }

  updateSearchOption(option: string) {
    this.searchOption = option;
    this.searchStudents(this.searchInput.value!.trim(), option);
  }

  triggerSearch() {
    this.searchStudents(this.searchInput.value!.trim(), this.searchOption);
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
        default:
          this.students$ = this.userService.getAllStudents();
          break;
      }
    } else {
      this.students$ = this.userService.getAllStudents(); // Reset to all students if search query is empty
    }
  }
}
