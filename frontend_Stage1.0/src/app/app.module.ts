import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; 
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';

import { AuthenticationService } from './services/authentication.service';
import { AdminMainComponent } from './components/admin-main/admin-main.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { UserManagementComponent } from './components/user-management/user-management.component';
import { StudentManagementTableComponent } from './components/student-management-table/student-management-table.component';
import { AdminManagementTableComponent } from './components/admin-management-table/admin-management-table.component';
import { ProfessorManagementTableComponent } from './components/professor-management-table/professor-management-table.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminMainComponent,
    SidebarComponent,
    UserManagementComponent,
    StudentManagementTableComponent,
    AdminManagementTableComponent,
    ProfessorManagementTableComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [AuthenticationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
