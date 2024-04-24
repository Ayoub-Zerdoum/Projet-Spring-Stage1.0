import { Component } from '@angular/core';
import { Observable, catchError, forkJoin, map, of, switchMap } from 'rxjs';

import { GoogleMapsModule } from '@angular/google-maps';



import { OfferManagementService } from '../../services/offer-management.service';
import { AuthenticationService } from '../../services/authentication.service';




@Component({
  selector: 'app-offers-student-component',
  templateUrl: './offers-student-component.component.html',
  styleUrls: ['./offers-student-component.component.css']
})
export class OffersStudentComponentComponent {
  selectedType: string = 'All'; // Default selected type
  currentUser: any | null = null;
  userType : string ='';
  offers$!: Observable<any[]>;
  Nboffers!: Observable<number>;
  currentPage: number = 0;
  totalPages: number[] = [0];
  offerToView: any | null = null;
  
  center: google.maps.LatLngLiteral = { lat: -34.397, lng: 150.644 };
  zoom = 8;
  options: google.maps.MapOptions = {
    mapTypeId: 'roadmap',
    zoomControl: true,
    scrollwheel: false,
    disableDoubleClickZoom: true,
    maxZoom: 25,
    minZoom: 8
  };

  markerPosition: google.maps.LatLngLiteral = { lat: 0, lng: 0 };


  setMarker(offersPosition: string): void {
    // Split the offersPosition string into latitude and longitude components
    const [latStr, lngStr] = offersPosition.split(',');

    // Convert the latitude and longitude strings to numbers
    const latVal = parseFloat(latStr);
    const lngVal = parseFloat(lngStr);

    // Create the markerPosition object
    this.markerPosition = { lat: latVal, lng: lngVal };

  }
  
 

  

  //markerPosition: google.maps.LatLngLiteral = { lat: -34.397, lng: 150.644 };
  

  constructor(private authService: AuthenticationService,private offerService: OfferManagementService) { 
    this.currentUser = this.authService.getUserInfo();
    this.userType = this.fetchUserType();
    this.showTable('All');
  }

  ngOnInit(): void {
    //calculateTotalPages(Nboffers);
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

  showTable(type: string,page: number = 0, size: number = 3): void {
    this.selectedType = type;
    this.currentPage = page;
    if (type === "All") {
      this.offerService.getAllOffersBySpecialisation(this.currentUser.userId, page, size).subscribe(([offers, nboffers]) => {
        this.offers$ = of(offers);
        this.Nboffers = of(nboffers);
        this.Nboffers.subscribe(totalOffers => {
          console.log(totalOffers);
          this.calculateTotalPages(totalOffers);
          console.log("nombre de pages total est :" + this.totalPages);
        });
      });
    } else if (type === "Mine") {
      this.offerService.getAllStudentOffers(this.currentUser.userId, page, size).subscribe(([offers, nboffers]) => {
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

  getRank(studentId: number,offerId: number): any{
    this.offerService.getStudentRankOffer(studentId,offerId).subscribe(
      (rank: number) => {
        return rank;
      },
      (error: any) => {
        console.error('Error fetching rank:', error);
      }
    );
  }

  // Method to add an offer
  addOffer(offerId: number, studentId: number): void {
    this.offerService.reserveOffer(offerId, studentId).subscribe(
      response => {
        console.log(response); // Log the response from the backend
        console.log("vous avez appliqué a cette offre avec success");
        this.showTable(this.selectedType,this.currentPage);
      },
      error => {
        console.error(error); // Log any errors
      }
    );
  }
}
