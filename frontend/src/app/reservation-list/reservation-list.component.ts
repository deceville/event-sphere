import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { ReservationService } from '../event-list/service/reservation.service';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './reservation-list.component.html',
  styleUrl: './reservation-list.component.css'
})
export class ReservationListComponent {
  reservations: any[] = [];
  userId: string = '';

  constructor(
    private reservationService: ReservationService,
    private authService: AuthService
  ) {
    this.userId = this.authService.getUserId();
  }

  ngOnInit(): void {
    this.loadReservations(this.userId);
  }

  loadReservations(userId: string): void {
    this.reservationService.getUserReservations(userId).subscribe({
      next: (reservations) => {
        this.reservations = reservations;
      },
      error: (error) => {
        let errorMessage = error.error || 'Error loading reservations.';
        alert(errorMessage);  // Show success message
      }
    });
  }

  cancelReservation(reservationId: string): void {
    this.reservationService.cancelReservation(reservationId).subscribe({
      next: (response) => {
        this.reservations = this.reservations.filter(
          (r) => r.id !== reservationId
        );
        alert(response);
      },
      error: (error) => {
        let errorMessage = error.error || 'Failed to cancel reservation.';
        alert(errorMessage);  // Show success message
      }
    });
  }
}
