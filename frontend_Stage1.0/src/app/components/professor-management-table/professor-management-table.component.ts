import { Component ,OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { UserManagementService } from '../../services/user-management.service';

@Component({
  selector: 'app-professor-management-table',
  templateUrl: './professor-management-table.component.html',
  styleUrls: ['./professor-management-table.component.css']
})
export class ProfessorManagementTableComponent implements OnInit{
  profs$!: Observable<any[]>;

  constructor(private userService: UserManagementService) { }

  ngOnInit(): void {
    this.profs$ = this.userService.getAllProfs();
  }
}
