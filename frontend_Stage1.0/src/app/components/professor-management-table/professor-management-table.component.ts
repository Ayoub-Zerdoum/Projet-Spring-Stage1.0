import { Component ,OnInit } from '@angular/core';

@Component({
  selector: 'app-professor-management-table',
  templateUrl: './professor-management-table.component.html',
  styleUrls: ['./professor-management-table.component.css']
})
export class ProfessorManagementTableComponent implements OnInit{
  professors: any[] = [
    { username: 'professor1', email: 'professor1@example.com', telephone: '1234567890', department: 'Computer Science', accountStatus: 'Active' },
    { username: 'professor2', email: 'professor2@example.com', telephone: '9876543210', department: 'Mathematics', accountStatus: 'Inactive' }
    // Add more professor data as needed
  ];

  constructor() { }

  ngOnInit(): void {
  }
}
