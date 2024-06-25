import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { PublicUserModel } from '../shared/PublicUserModel';
import { ProfileService } from './profile.service';

@Injectable({
  providedIn: 'root'
})
export class UserModelResolver implements Resolve<void> {
  constructor(
    private authService: AuthService,
    private publicUserModel: PublicUserModel,
    private profileService: ProfileService
  ) {}

  resolve(): Observable<void> {
    const info = this.authService.getUserInfoFromToken();
    if (info) {
      const username = info.username;
      return new Observable(observer => {
        this.profileService.getProfile(username).subscribe(
          user => {
            this.publicUserModel.setUserModel(user);
            observer.next();
            observer.complete();
          },
          error => {
            console.error('Error getting user:', error);
            observer.error(error);
          }
        );
      });
    }
    return new Observable(observer => {
      observer.next();
      observer.complete();
    });
  }
}