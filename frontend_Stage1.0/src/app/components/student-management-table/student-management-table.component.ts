import { Component , OnInit} from '@angular/core';
import { Observable } from 'rxjs';
import { FormControl } from '@angular/forms';
import { of } from 'rxjs';



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
      if (option === 'Username') {
        this.userService.searchStudentsByUsername(searchQuery).subscribe({
          next: students => {
            console.log('Search result:', students);
            this.students$ = of(students);
          },
          error: error => {
            console.error('Error searching students:', error);
          }
        });
      } else {
        // Implement other search options here if needed
      }
    } else {
      this.students$ = this.userService.getAllStudents(); // Reset to all students if search query is empty
    }
  }
}
