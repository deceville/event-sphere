import { Component, OnInit } from '@angular/core';
import { EventService } from './service/event.service';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css', '../shared/shared-styles.css']
})
export class EventListComponent implements OnInit {
  events: any[] = []; // Array to store events
  role: string = ''; // Store user role
  errorMessage: string | null = null; // Store error message for failures

  constructor(
    private eventService: EventService, 
    private authService: AuthService, 
    private router: Router
  ) {
    this.role = this.authService.getUserRole();
  }

  ngOnInit(): void {
    // Fetch the list of events on component initialization
    this.loadEvents();
  }

  // Function to load events
  loadEvents(): void {
    // Load events
    this.eventService.getEvents().subscribe({
      next: (event) => {
        this.events = event.sort((a, b) => a.id - b.id);
      },
      error: (error) => {
        this.errorMessage = 'Failed to load events';
        console.error('Error loading event', error);
      }
    });
  }

  // Check if the user is an ORGANIZER or ADMIN
  isOrganizerOrAdmin(): boolean {
    return this.role.includes('ROLE_ORGANIZER') || this.role.includes('ROLE_ADMIN');
  }

  // Function to view an event by ID (navigate to event detail)
  viewEvent(eventId: string): void {
    this.router.navigate([`/events/view/${eventId}`]); // Navigate to event detail page
  }

  // Function to create a new event (navigate to create event form)
  createEvent(): void {
    this.router.navigate(['/events/manage']); // Navigate to the create event page
  }

  // Function to update an existing event (navigate to update event form)
  updateEvent(eventId: string): void {
    this.router.navigate([`/events/manage/${eventId}`]); // Navigate to update event page
  }

  // Function to delete an event by ID
  deleteEvent(eventId: string): void {
    if (confirm('Are you sure you want to delete this event?')) {
      this.eventService.deleteEvent(eventId).subscribe({
        next: () => {
          this.loadEvents(); // Reload the events after deletion
        },
        error: (error) => {
          this.errorMessage = 'Failed to delete event';
          console.error('Error deleting event', error);
        }
      });
    }
  }

  registerForEvent(eventId: number): void {
    // Redirect to the reservation page or show a registration modal
    this.router.navigate(['/reserve', eventId]);
  }
}