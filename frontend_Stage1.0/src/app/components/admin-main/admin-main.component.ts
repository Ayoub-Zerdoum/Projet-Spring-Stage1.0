import { Component } from '@angular/core';

import { AuthenticationService } from '../../services/authentication.service';


@Component({
  selector: 'app-admin-main',
  templateUrl: './admin-main.component.html',
  styleUrls: ['./admin-main.component.css']
})
export class AdminMainComponent {
  selectedOption: string = '';
  currentUser: any | null = null;
  userType : string ='';

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void{
    this.currentUser = this.authService.getUserInfo();
    this.userType = this.fetchUserType();
  }

  handleSidebarOptionClicked(option: string) {
    this.selectedOption = option;
  }

  fetchUserType(): any {
    this.authService.getUserTypeById(this.currentUser.userId).subscribe(
      (userType: string) => {
        this.userType = userType; 
      },
      (error: any) => {
        console.error('Error fetching user type:', error);
      }
    );
  }
}
