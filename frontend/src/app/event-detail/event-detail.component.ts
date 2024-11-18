import { Component } from '@angular/core';
import { EventService } from '../event-list/service/event.service';
import { ReservationService } from '../event-list/service/reservation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-event-detail',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css', '../shared/shared-styles.css']
})
export class EventDetailComponent {
  eventId: string | null = '';
  event: any = {};
  role: string = '';
  isEditing: boolean = false;
  reservation: any;
  userId: string = '';
  reservationLength: number = 0;
  isLoggedIn = false;

  constructor(
    private eventService: EventService,
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private reservationService: ReservationService
  ) {
    this.role = this.authService.getUserRole();
    this.userId = this.authService.getUserId();
  }

  ngOnInit(): void {
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    this.eventId = this.route.snapshot.paramMap.get('id');
    if (this.eventId) {
      this.loadEventDetails(this.eventId);
      if (this.isLoggedIn) {
        this.checkUserReservation(this.eventId);
      }
    }
  }

  loadEventDetails(eventId: string): void {
    this.eventService.getEventById(eventId).subscribe({
      next: (event) => {
        this.event = event;
        this.reservationLength = event.reservations.length;
      },
      error: (error) => {
        console.error('Error loading event details', error);
      }
    });
  }

  checkUserReservation(eventId: string): void {
    this.reservationService.getUserReservations(this.userId).subscribe({
      next: (reservations) => {
        this.reservation = reservations.find((r: any) => r.eventId === eventId);
      },
      error: (error) => {
        console.error('Error checking user reservations', error);
      }
    });
  }

  bookReservation(eventId: string): void {
    if (!this.role.includes('ROLE_AUDIENCE') && !this.role.includes('ROLE_ADMIN')) {
      alert('You are not authorized to book this event');
      return;
    }

    this.reservationService.bookReservation(eventId, this.userId).subscribe({
      next: (reservation) => {
        this.reservation = reservation;
        alert('Reservation successful!');
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate([`/events/view/${eventId}`]);
        });
      },
      error: (error) => {
        console.error('Error booking reservation', error);
        alert('Failed to reserve event');
      }
    });
  }

  // Cancel the reservation
  cancelReservation(reservationId: string): void {
    this.reservationService.cancelReservation(reservationId).subscribe({
      next: (response) => {
        alert(response);  // Show success message
        this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
          this.router.navigate([`/events/view/${this.eventId}`]);
        });
        this.reservation = [];
      },
      error: (error) => {
        let errorMessage = error.error || 'An unexpected error occurred.';
        alert(errorMessage);  // Show success message
      }
    });
  }

  // Function to handle canceling (deleting) the event
  cancelEvent(eventId: string): void {
    if (confirm('Are you sure you want to cancel this event?')) {
      this.eventService.deleteEvent(eventId).subscribe({
        next: () => {
          this.router.navigate(['/events/view']); // Redirect to the events list after deletion
        },
        error: (error) => {
          console.error('Error deleting event', error);
        }
      });
    }
  }

  // Function to handle returning to previous page
  backEvent(): void {
    this.router.navigate(['/events/view']); // Redirect to the events list after deletion
  }
}
