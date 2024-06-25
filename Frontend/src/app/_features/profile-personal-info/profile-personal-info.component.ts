// profile-personal-info.component.ts

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import { AuthService } from '../../_services/auth.service';
import {UserModelDTO} from "../../_models/usermodel.model";
import {PublicUserModel} from "../../shared/PublicUserModel";
import { ProfileService } from '../../_services/profile.service';
import { Subscription } from 'rxjs';
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-profile-personal-info',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule,DefaultImageDirective],
  templateUrl: './profile-personal-info.component.html',
  styleUrls: ['./profile-personal-info.component.css']
})
export class ProfilePersonalInfoComponent implements OnInit {
  userData: UserModelDTO | null | undefined;
  private userModelSubscription: Subscription | undefined;

  constructor(private http: HttpClient, private authService: AuthService, private cdr: ChangeDetectorRef,private publicUserModel: PublicUserModel,private profileService:ProfileService) {}

  ngOnInit() {
   
    this.userModelSubscription = this.publicUserModel.userModel$.subscribe(
      (user) => {
        this.userData = user;
      }
    );
  }


  onSubmit() {
    if (this.userData) {
      this.profileService.updateProfile(this.userData).subscribe(
        (updatedUser) => {
          console.log('Profile updated successfully:', updatedUser);
          // Update the PublicUserModel with the new data
          this.publicUserModel.setUserModel(updatedUser);
        },
        (error) => {
          console.error('Error updating profile:', error);
        }
      );
    } else {
      console.error('Cannot update profile: userData is null');
    }
  }
}
