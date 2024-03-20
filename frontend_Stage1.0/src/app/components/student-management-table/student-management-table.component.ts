import { Component , OnInit} from '@angular/core';

@Component({
  selector: 'app-student-management-table',
  templateUrl: './student-management-table.component.html',
  styleUrls: ['./student-management-table.component.css']
})
export class StudentManagementTableComponent implements OnInit{
  students: any[] = [
    { username: 'student1', email: 'student1@example.com', telephone: '1234567890', status: 'Active', specialite: 'Computer Science', dateOfBirth: '1990-01-01', account: 'Active' },
    { username: 'student2', email: 'student2@example.com', telephone: '9876543210', status: 'Inactive', specialite: 'Mathematics', dateOfBirth: '1995-05-05', account: 'Inactive' }
  ];

  ngOnInit(): void {
  }
}
