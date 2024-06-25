import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "../_services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserMatchGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const token = localStorage.getItem('token'); // Adjust this based on where you store the JWT

    if (token) {
      const jwtUsername = this.authService.getUsername(); // Adjust 'username' to match your JWT claim name
      const urlUsername = route.params['username'];

      if (jwtUsername === urlUsername) {
        return true;
      }
    }

    // Redirect to an unauthorized page or home page if the user doesn't match
    return this.router.createUrlTree(['/unauthorized']);
  }
}
