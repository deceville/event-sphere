import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, BehaviorSubject, catchError, throwError, tap } from "rxjs";
import { environment } from "../../environment";
import { jwtDecode } from 'jwt-decode';
import { response } from "express";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = environment.apiUrl; // Base URL for your backend
  private tokenKey = 'authToken'; // LocalStorage key for JWT token
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.getStoredLoginState()); // BehaviorSubject to hold the state
  private usernameSubject = new BehaviorSubject<string | null>(null); // BehaviorSubject to hold the state

  constructor(private http: HttpClient) {
    // Initialize the login state from localStorage if present
    if (this.isLocalStorageAvailable()) {
      const storedLoginState = localStorage.getItem('isLoggedIn');
      if (storedLoginState === 'true') {
        this.isLoggedInSubject.next(true);
      }
      const savedUsername = localStorage.getItem('username');
      if (savedUsername) {
        this.usernameSubject.next(savedUsername);
      }
    }
  }

  // Observable to be used by components
  isLoggedIn$ = this.isLoggedInSubject.asObservable();
  username$ = this.usernameSubject.asObservable();

  // Login method to get JWT token
  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/auth/login`, { username, password }).pipe(
      tap((response) => {
        if (this.isLocalStorageAvailable()) {
          localStorage.setItem('isLoggedIn', 'true');
        }
        this.isLoggedInSubject.next(true);
      }),
      catchError((error) => {
        try {
          // Attempt to parse error message if it's JSON
          const parsedError = JSON.parse(error.error);
          console.error('Parsed Error:', parsedError);
          return throwError(() => new Error(parsedError.message || 'Unknown error occurred.'));
        } catch (parseError) {
          // Handle cases where error response is not JSON
          console.error('SyntaxError: Invalid JSON response:', error.error);
          return throwError(() => new Error('Invalid server response. Please try again later.'));
        }
      })
    );
  }

  // Remove the token from localStorage
  logout(): void {
    this.isLoggedInSubject.next(false);
    if (this.isLocalStorageAvailable()) {
      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('username');
      localStorage.removeItem(this.tokenKey);
    }
  }

  // Method to get the stored login state (returns a boolean)
  private getStoredLoginState(): boolean {
    if (this.isLocalStorageAvailable()) {
      const storedState = localStorage.getItem('isLoggedIn');
      return storedState === 'true';
    }
    return false;
  }

  // Register new user
  register(registerData: any): Observable<any> {
    registerData.role = `ROLE_${registerData.role}`;
    return this.http.post<any>(`${this.apiUrl}/auth/register`, registerData);
  }

  // Check if localStorage is available
  private isLocalStorageAvailable(): boolean {
    return typeof window !== 'undefined' && typeof window.localStorage !== 'undefined';
  }

  // Store the JWT token in localStorage
  setToken(token: string): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem(this.tokenKey, token);
    }
  }

  // Get the JWT token from localStorage
  getToken(): string | null {
    if (this.isLocalStorageAvailable()) {
      return localStorage.getItem(this.tokenKey);
    }
    return null;
  }

  // Get the current user ID
  getUserId(): string {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token); // Decode JWT to extract roles
      return decodedToken.userId || '';
    }

    return '';
  }

  // Get the current username
  getUsername(): string {
    if (this.isLocalStorageAvailable()) {
      return localStorage.getItem('username') || '';
    }
    return '';
  }

  updateUsername(username: string): void {
    this.usernameSubject.next(username);
    localStorage.setItem('username', username);
  }

  clearUsername(): void {
    this.usernameSubject.next(null);
    localStorage.removeItem('username');
  }

  // Check if user is authenticated (i.e., token exists)
  isAuthenticated(): boolean {
    return !!this.getToken(); // True if token exists, false otherwise
  }

  // Send HTTP requests with Authorization header
  getHeaders(): HttpHeaders {
    const token = this.getToken();
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // Get roles (e.g., after user login)
  getUserRole(): string {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = jwtDecode(token); // Decode JWT to extract roles
      return decodedToken.role || '';
    }

    return '';
  }
}