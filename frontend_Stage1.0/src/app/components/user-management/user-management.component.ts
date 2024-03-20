import { Component } from '@angular/core';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent {
  selectedType: string = 'student'; // Default selected type

  constructor() { }

  showTable(type: string): void {
    this.selectedType = type;
  }
}
