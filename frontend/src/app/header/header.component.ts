import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SHARED_MODULE } from '../shared/shared-modules';
import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [...SHARED_MODULE],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css', '../shared/shared-styles.css'],
  template: `<router-outlet></router-outlet>`,
})
export class HeaderComponent {
  isLoggedIn = false; // Change this to true when the user is logged in
  username: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    // Subscribe to the auth service to listen for changes in login state
    this.authService.isLoggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
    this.authService.username$.subscribe((username) => {
      this.username = username;
    });
  }

  onLogout() {
    this.authService.logout();
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/auth/login']);
    });
  }

  onLogin() {
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate(['/auth/login']);
    });
  }
}
