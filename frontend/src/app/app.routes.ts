import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { EventListComponent } from './event-list/event-list.component';
import { RegisterComponent } from './register/register.component';
import { EventDetailComponent } from './event-detail/event-detail.component';
import { EventCreateComponent } from './event-create/event-create.component';
import { ReservationListComponent } from './reservation-list/reservation-list.component';

export const routes: Routes = [
  { path: 'events/view', component: EventListComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'events/view/:id', component: EventDetailComponent },
  { path: 'events/manage', component: EventCreateComponent },
  { path: 'events/manage/:id', component: EventCreateComponent },
  { path: 'reservations/', component: ReservationListComponent },
  { path: '', redirectTo: '/events/view', pathMatch: 'full' }, // Default route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],  // Configures routing
  exports: [RouterModule]  // Exports to make available to other modules
})
export class AppRoute { }