// profile-personal-info.component.ts

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import { AuthService } from '../../_services/auth.service';
import {UserModel,Gender,Status} from "../../_models/usermodel.model";

@Component({
  selector: 'app-profile-personal-info',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './profile-personal-info.component.html',
  styleUrls: ['./profile-personal-info.component.css']
})
export class ProfilePersonalInfoComponent implements OnInit {
  userData: UserModel = {} as UserModel;

  constructor(private http: HttpClient, private authService: AuthService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.loadUserData();
  }

  loadUserData() {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo) {
      this.fetchAdditionalUserData(userInfo.email);
    } else {
      console.error('No user information available');
    }
  }

  fetchAdditionalUserData(email: string) {
    this.http.get<UserModel>(`http://localhost:8081/profile/edit/${email}`).subscribe({
      next: (data) => {
        this.userData = {...this.userData, ...data};
        console.log("Data Binded to UI");
        // Manually trigger change detection
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error fetching user data:', error);
      }
    });
  }

  onSubmit() {
    // Prepare the data for submission
    const updatedUserData = {...this.userData}; // Make a copy of userData to avoid direct mutation

    // Make the PUT request to update the profile info
    this.http.put(`http://localhost:8081/profile/edit`, updatedUserData,).subscribe({
      next: (response) => {
        console.log(response);
        // Handle successful update (e.g., show success message)
      },
      error: (error) => {
        console.error('Error updating user data:', error);
        // Handle error (e.g., show error message)
      }
    });
  }
}
