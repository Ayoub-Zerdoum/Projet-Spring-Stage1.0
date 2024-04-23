import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, forkJoin, map, switchMap } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OfferManagementService {

  private baseUrl = environment.apiUrl; // Assuming your backend APIs are served from this base URL

  constructor(private http: HttpClient) { }

  // Function to fetch all offers for a student with pagination
  getAllStudentOffers(studentId: number, page: number = 0, size: number = 3): Observable<[any[], number]> {
    // Fetch the offers first
    const offers$ = this.http.get<any[]>(`${this.baseUrl}/students/offre/all?studentId=${studentId}&page=${page}&size=${size}`);
    // Fetch the total number of offers
    const totalOffers$ = this.http.get<number>(`${this.baseUrl}/students/offre/all/taille/${studentId}`);
    // Combine both observables and emit the result as a tuple
    return forkJoin([offers$, totalOffers$]).pipe(
      map(([offers, totalOffers]) => [offers, totalOffers] as [any[], number])
    );
  }

  getAllOffersBySpecialisation(studentId: number, page: number = 0, size: number = 3): Observable<[any[], number]> {
    // Fetch the offers first
    const offers$ = this.http.get<any[]>(`${this.baseUrl}/offers/specialization/all/${studentId}?page=${page}&size=${size}`);
    
    // Fetch the total number of offers
    const totalOffers$ = this.http.get<number>(`${this.baseUrl}/offers/specialization/all/taille/${studentId}`);

    // Combine both observables and emit the result as a tuple
    return forkJoin([offers$, totalOffers$]).pipe(
      map(([offers, totalOffers]) => [offers, totalOffers] as [any[], number])
    );
  }

  // Function to fetch all offers with pagination
  getAllOffers(page: number = 0, size: number = 5): Observable<[any[], number]> {
    const offers$ = this.http.get<any[]>(`${this.baseUrl}/offers/all?page=${page}&size=${size}`);
    const totalOffers$ = this.http.get<number>(`${this.baseUrl}/offers/all/taille`);
    // Combine both observables and emit the result as a tuple
    return forkJoin([offers$, totalOffers$]).pipe(
      map(([offers, totalOffers]) => [offers, totalOffers] as [any[], number])
    );
  }

  // Function to add an offer for a student
  reserveOffer(offerId: number, studentId: number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/students/offre/add?offerId=${offerId}&studentId=${studentId}`, {});
  }

  // Function to get the rank of a student in an offer
  getStudentRankOffer(studentId: number, offerId: number): Observable<number> {
    //return this.http.get<any>(`${this.baseUrl}/students/offre/rank?studentId=${studentId}&offerId=${offerId}`);
    return this.http.get<{ rank: number }>(`${this.baseUrl}/students/offre/rank?studentId=${studentId}&offerId=${offerId}`)
      .pipe(map(response => response.rank));
  }

  addOffer(offerId: number, studentId: number): Observable<any> {
    const url = `${this.baseUrl}/students/offre/add`;
    const params = {
      offerId: offerId.toString(),
      studentId: studentId.toString()
    };

    return this.http.post<any>(url, null, { params });
  }
}
