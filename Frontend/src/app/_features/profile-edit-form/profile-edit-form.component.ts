import { Component } from '@angular/core';
import {ProfilePersonalInfoComponent} from "../profile-personal-info/profile-personal-info.component";
import {ProfileChangePasswordComponent} from "../profile-change-password/profile-change-password.component";
import {ProfileEditEmailComponent} from "../profile-edit-email/profile-edit-email.component";
import {ProfileManageContactComponent} from "../profile-manage-contact/profile-manage-contact.component";

@Component({
  selector: 'app-profile-edit-form',
  standalone: true,
  imports: [
    ProfilePersonalInfoComponent,
    ProfileChangePasswordComponent,
    ProfileEditEmailComponent,
    ProfileManageContactComponent
  ],
  templateUrl: './profile-edit-form.component.html',
  styleUrl: './profile-edit-form.component.css'
})
export class ProfileEditFormComponent {

}
