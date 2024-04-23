import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; 
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './services/token.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';


import { GoogleMapsModule } from '@angular/google-maps'




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
import { OffersStudentComponentComponent } from './components/offers-student-component/offers-student-component.component';
import { OffersAdminComponent } from './components/offers-admin/offers-admin.component';


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
    OffersStudentComponentComponent,
    OffersAdminComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    GoogleMapsModule
  ],
  providers: [
    AuthenticationService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
