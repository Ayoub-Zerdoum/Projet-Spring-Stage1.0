import { Component ,OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-management-table',
  templateUrl: './admin-management-table.component.html',
  styleUrls: ['./admin-management-table.component.css']
})
export class AdminManagementTableComponent implements OnInit{
  admins: any[] = [
    { username: 'admin1', email: 'admin1@example.com', telephone: '1234567890', privilege: 'Administrator', accountStatus: 'Active' },
    { username: 'admin2', email: 'admin2@example.com', telephone: '9876543210', privilege: 'Supervisor', accountStatus: 'Inactive' }
    // Add more admin data as needed
  ];

  constructor() { }

  ngOnInit(): void {
  }
}
