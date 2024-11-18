import { Component } from '@angular/core';
import { EventService } from '../event-list/service/event.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-event-create',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.css', '../shared/shared-styles.css']
})
export class EventCreateComponent {
  event: any = {}; // This will hold the event data for both creating and editing
  isEditing: boolean = false; // Track whether we're editing an event or creating a new one
  eventId: string | null = ''; // To hold the ID of the event when editing
  formattedDate = new Date(); // Default value

  constructor(
    private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Check if the component is in edit mode by checking if there's an event ID in the route
    this.eventId = this.route.snapshot.paramMap.get('id');
    if (this.eventId) {
      this.isEditing = true;
      this.loadEventForEdit(this.eventId); // Load the event details if editing
    }
  }

  loadEventForEdit(eventId: string): void {
    this.eventService.getEventById(eventId).subscribe({
      next: (event) => {
        this.event = event; // Populate the event data
      },
      error: (error) => {
        console.error('Error loading event', error);
      }
    });
  }

  onSubmit(): void {
    if (this.isEditing) {
      // Update the event if in editing mode
      this.eventService.updateEvent(this.event.id, this.event).subscribe({
        next: (response) => {
          alert('Updated event successfully!');
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate(['/events/view']);
          });
        },
        error: (error) => {
          console.error('Error updating event', error);
        }
      });
    } else {
      // Create a new event if not in editing mode
      this.eventService.createEvent(this.event).subscribe({
        next: (response) => {
          alert('Registered event successfully!');
          this.router.navigate(['/events/view']); // Redirect to the events list after creation
        },
        error: (error) => {
          console.error('Error creating event', error);
        }
      });
    }
  }
  
  // Function to handle returning to previous page
  backEvent(): void {
    this.router.navigate(['/events/view']); // Redirect to the events list after deletion
  }

  onDateChange(event: any) {
    const selectedDate = event.date;
    if (event.date) {
      this.event.date = this.formatDate(selectedDate);
    }
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');  // Months are zero-indexed
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;  // Format to 'YYYY-MM-DD'
  }
}
