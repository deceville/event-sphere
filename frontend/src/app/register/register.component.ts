import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router } from '@angular/router';
import { SHARED_MODULE } from '../shared/shared-modules';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css', '../shared/shared-styles.css']
})
export class RegisterComponent {
  registerData = {
    username: '',
    firstname: '',
    lastname: '',
    role: '',
    password: '',
    confirmPassword: ''
  };
  roles: string[] = ['ADMIN', 'ORGANIZER', 'AUDIENCE'];
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onRegister(): void {
    if (this.registerData.password !== this.registerData.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }
    this.authService.register(this.registerData).subscribe({
      next: (response) => {
        alert('Registration successful! Please login.');
        this.router.navigate(['/auth/login']); // Redirect to the events page
      },
      error: (error) => {
        // Handle the error and set the error message
        this.errorMessage = 'Please enter required fields.';
        console.error('Registration failed', error);
      }
    });
  }

  loginEvent(): void {
    this.router.navigate(['/auth/login']);
  }
}
