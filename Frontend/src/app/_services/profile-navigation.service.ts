import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class ProfileNavigationService {
  constructor(private router: Router, private authService : AuthService) {}

  navigateToProfile() {
    const token = localStorage.getItem('token');
    if (token) {
      const username = this.authService.getUsername();
      this.router.navigate(['/profile', username]);
    } else {
      // Handle the case when there's no token (user not logged in)
      this.router.navigate(['/login']);
    }
  }
}
