import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

import { OfferManagementService } from '../../services/offer-management.service';
import { AuthenticationService } from '../../services/authentication.service';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-offers-admin',
  templateUrl: './offers-admin.component.html',
  styleUrls: ['./offers-admin.component.css']
})
export class OffersAdminComponent {
  currentUser: any | null = null;
  userType : string ='';
  offers$!: Observable<any[]>;
  Nboffers!: Observable<number>;
  currentPage: number = 0;
  totalPages: number[] = [0];
  offerToView: any | null = null;
  selectedType: string = 'All'; // Default selected type
  selectedNewOfferSpecialization: string | null = null;
  selectedSpecialization: string | null = null;

  activeFilters: number = 0;
  filterChanged: boolean = false;

  addingActive: boolean = false; 
  searchOption: string = 'Username';
  searchInput = new FormControl('');

  constructor(private authService: AuthenticationService,private offerService: OfferManagementService){
    this.currentUser = this.authService.getUserInfo();
    this.userType = this.currentUser.role;
    this.showTable('All');
  }

  selectNewOfferSpecialization(specialization: string): void {
    this.selectedNewOfferSpecialization = specialization;
  }

  toggleSpecialization(specialization: string){
    if (this.selectedSpecialization === specialization) {
      this.selectedSpecialization = null; // Uncheck if it's already selected
    } else {
      this.selectedSpecialization = specialization; // Check if it's not selected
    }
    this.updateActiveFilters();
  }

  updateActiveFilters(): void {
    // Reset count to 0 before counting active filters
    this.activeFilters = 0;
    this.filterChanged = true;

    if (this.selectedSpecialization !== null) {
        this.activeFilters++;
    }
  }

  applyFilters(): void {
    const filters: any  = {
      specialization: this.selectedSpecialization || null
    };

    
    // Remove null parameters from the object
    Object.keys(filters).forEach(key => filters[key] === null && delete filters[key]);

  
    // Call the service method to apply the filters
      this.searchInput.setValue('');
      this.filterChanged = false;
      //this.offers$ = this.offerService.filterStudents(filters);
  }

  triggerSearch() {
    //this.searchOffers(this.searchInput.value!.trim(), this.searchOption);

    this.selectedSpecialization = null;
    this.filterChanged = false;
    this.activeFilters = 0;
  }
/*
  searchStudents(searchQuery: string, option: string) {
    console.log('Searching students with query:', searchQuery);
    if (searchQuery) {
      switch(option) {
        case 'Username':
          this.students$ = this.userService.searchStudentsByUsername(searchQuery);
          break;
        case 'Email':
          this.students$ = this.userService.searchStudentsByEmail(searchQuery);
          break;
        case 'Telephone':
          this.students$ = this.userService.searchStudentsByTelephone(searchQuery);
          break;
        case 'DateOfBirth':
          this.students$ = this.userService.searchStudentsByDateOfBirth(searchQuery);
          break;
        default:
          this.students$ = this.userService.getAllStudents();
          break;
      }
    } else {
      this.students$ = this.userService.getAllStudents(); // Reset to all students if search query is empty
    }
  }*/

  toggleAddingActive(): void {
    this.addingActive = !this.addingActive;
  }

  updateSearchOption(option: string) {
    this.searchOption = option;
    //this.searchStudents(this.searchInput.value!.trim(), option);
  }

  showTable(type: string,page: number = 0, size: number = 3): void {
    this.selectedType = type;
    this.currentPage = page;
    if (type === "All") {
      this.offerService.getAllOffers( page, size).subscribe(([offers, nboffers]) => {
        this.offers$ = of(offers);
        this.Nboffers = of(nboffers);
        this.Nboffers.subscribe(totalOffers => {
          console.log(totalOffers);
          this.calculateTotalPages(totalOffers);
          console.log("nombre de pages total est :" + this.totalPages);
        });
      });
    } else if (type === "Mine") {
      this.offerService.getAllOffers(/*this.currentUser.userId,*/ page, size).subscribe(([offers, nboffers]) => {
        this.offers$ = of(offers);
        this.Nboffers = of(nboffers);
        this.Nboffers.subscribe(response => {
          const totalOffers = response;
          console.log(totalOffers);
          this.calculateTotalPages(totalOffers);
          console.log("nombre de pages total est :" + this.totalPages);
        });
      });
    }
  }

  private calculateTotalPages(totalOffers: number): void {
    const totalPages = Math.ceil(totalOffers / 3); // Assuming pageSize is 3
    console.log(totalPages);
    this.totalPages = Array.from({ length: totalPages }, (_, index) => index);
  }

  activateOffer(offer:any){}
  suspendOffer(offer:any){}
  DeleteThisStudent(offer:any){}
  setOfferToEdit(offer:any){}
}
