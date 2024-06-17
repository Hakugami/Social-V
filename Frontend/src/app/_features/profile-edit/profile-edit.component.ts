import { Component } from '@angular/core';
import {ProfileEditHeaderComponent} from "../profile-edit-header/profile-edit-header.component";
import {ProfileEditFormComponent} from "../profile-edit-form/profile-edit-form.component";

@Component({
  selector: 'app-profile-edit',
  standalone: true,
  imports: [
    ProfileEditHeaderComponent,
    ProfileEditFormComponent
  ],
  templateUrl: './profile-edit.component.html',
  styleUrl: './profile-edit.component.css'
})
export class ProfileEditComponent {

}
