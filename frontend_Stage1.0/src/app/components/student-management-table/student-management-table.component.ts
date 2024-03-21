import { Component , OnInit} from '@angular/core';
import { Observable } from 'rxjs';

import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-student-management-table',
  templateUrl: './student-management-table.component.html',
  styleUrls: ['./student-management-table.component.css']
})
export class StudentManagementTableComponent implements OnInit{
  students$!: Observable<any[]>;

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.students$ = this.userService.getAllStudents();
  }
}
