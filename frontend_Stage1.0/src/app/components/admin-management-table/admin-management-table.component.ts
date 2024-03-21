import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-admin-management-table',
  templateUrl: './admin-management-table.component.html',
  styleUrls: ['./admin-management-table.component.css']
})
export class AdminManagementTableComponent implements OnInit{
  admins$!: Observable<any[]>;

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.admins$ = this.userService.getAllAdmins();
  }
}
