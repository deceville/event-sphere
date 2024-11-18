import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../../environment';
import { AuthService } from '../../auth/auth.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = `${environment.apiUrl}/events`;

  constructor(private http: HttpClient, private authService: AuthService, private router: Router) { }

  getEvents(): Observable<any[]> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.get<any>(`${this.apiUrl}/view`, { headers }).pipe(
      catchError((error) => {
        if (error.status === 401) {
          console.error('Unauthorized: You are not authorized to view events.');
          // Handle the error by redirecting to login or showing a notification
          this.router.navigate(['/login']);
        } else {
          console.error('An unexpected error occurred', error);
        }
        return throwError(error); // Throw the error for further handling
      })
    );
  }

  // Fetch a single event by ID
  getEventById(eventId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.get<any>(`${this.apiUrl}/view/${eventId}`, { headers });
  }

  // Create a new event
  createEvent(eventData: any): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.post<any>(`${this.apiUrl}/manage`, eventData, { headers });
  }

  // Update an existing event
  updateEvent(eventId: string, updatedData: any): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.put<any>(`${this.apiUrl}/manage/${eventId}`, updatedData, { headers });
  }

  // Delete an event by ID
  deleteEvent(eventId: string): Observable<any> {
    const headers = this.authService.getHeaders(); // Get the headers with token
    return this.http.delete<any>(`${this.apiUrl}/manage/${eventId}`, { headers });
  }
}
