import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserModelDTO } from "../../_models/usermodel.model";
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-friend-profile-header',
  standalone: true,
  imports: [CommonModule, DefaultImageDirective],
  templateUrl: './friend-profile-header.component.html',
  styleUrl: './friend-profile-header.component.css'
})
export class FriendProfileHeaderComponent {
  @Input() friendUser: UserModelDTO | null = null;
}