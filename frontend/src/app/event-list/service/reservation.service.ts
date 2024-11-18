import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment';
import { AuthService } from '../../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private apiUrl = `${environment.apiUrl}/reservations`;

  constructor(private http: HttpClient, private authService: AuthService) { }

  // Get all reservations for a specific user
  getUserReservations(userId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.get<any>(`${this.apiUrl}/user/${userId}`, { headers });
  }

  // Get reservation details by reservation ID
  getReservationById(reservationId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.get<any>(`${this.apiUrl}/${reservationId}`, { headers });
  }

  // Book a reservation for an event
  bookReservation(eventId: string, userId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.post<any>(`${environment.apiUrl}/events/reserve/${eventId}`, { eventId, userId }, { headers });
  }

  // Cancel a reservation
  cancelReservation(reservationId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.delete<any>(`${this.apiUrl}/${reservationId}/cancel`, { headers, responseType: 'text' as 'json' });
  }
}
