import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css', '../shared/shared-styles.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin(): void {
    if (this.username && this.password) {
      this.authService.login(this.username, this.password).subscribe({
        next: (response) => {
          if (response && response.token) {
            // If login is successful, store the token and redirect
            this.authService.setToken(response.token); // Assuming token is in the response
            this.authService.updateUsername(this.username);
            this.router.navigate(['/events/view']); // Redirect to the events page
          }
        },
        error: (error) => {
          this.errorMessage = error.error || 'Invalid username or password.';
          console.error('Login failed', error);
          return of(null); // Return null to stop the observable chain
        }
      });
    } else {
      this.errorMessage = 'Please enter both username and password.';
    }
  }

  registerEvent(): void {
    this.router.navigate(['/auth/register']);
  }
}
