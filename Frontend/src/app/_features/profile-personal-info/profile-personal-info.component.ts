import { Component, OnInit, ChangeDetectorRef, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../_services/auth.service';
import { UserModelDTO } from "../../_models/usermodel.model";
import { PublicUserModel } from "../../shared/PublicUserModel";
import { ProfileService } from '../../_services/profile.service';
import { Subscription } from 'rxjs';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import {ProfileNavigationService} from "../../_services/profile-navigation.service";

@Component({
  selector: 'app-profile-personal-info',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, DefaultImageDirective],
  templateUrl: './profile-personal-info.component.html',
  styleUrls: ['./profile-personal-info.component.css']
})
export class ProfilePersonalInfoComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  userData: UserModelDTO | null | undefined;
  private userModelSubscription: Subscription | undefined;
  selectedFile: File | null = null;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private publicUserModel: PublicUserModel,
    private profileService: ProfileService,
    private profileNavigationService: ProfileNavigationService
  ) {}

  ngOnInit() {
    this.userModelSubscription = this.publicUserModel.userModel$.subscribe(
      (user) => {
        this.userData = user;
      }
    );
  }

  triggerFileInput() {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
      this.previewImage(this.selectedFile);
    }
  }

  previewImage(file: File) {
    const reader = new FileReader();
    reader.onload = (e: any) => {
      if (this.userData) {
        this.userData.profilePicture = e.target.result;
      }
    };
    reader.readAsDataURL(file);
  }

  private createFormData(userDto: Partial<UserModelDTO>, files: File[]): FormData {
    const formData = new FormData();

    // Append the entire userDto as a JSON string
    formData.append('dto', JSON.stringify(userDto));

    if (files.length > 0) {
      formData.append('file', files[0], files[0].name);
    }

    return formData;
  }

  onSubmit() {
    if (this.userData) {
      const formData = this.createFormData(this.userData, this.selectedFile ? [this.selectedFile] : []);
      this.updateProfile(formData);
    } else {
      console.error('Cannot update profile: userData is null');
    }
  }

  private updateProfile(formData: FormData) {
    this.profileService.updateProfile(formData).subscribe(
      (updatedUser) => {
        console.log('Profile updated successfully:', updatedUser);
        this.publicUserModel.setUserModel(updatedUser);
        this.profileNavigationService.navigateToProfile();
      },
      (error) => {
        console.error('Error updating profile:', error);
      }
    );
  }
}
