import { Component, OnInit, OnDestroy } from '@angular/core';
import { RouterLink } from "@angular/router";
import { NotificationDropdownComponent } from "../notification-dropdown/notification-dropdown.component";
import { FriendRequestDropdownComponent } from "../friend-request-dropdown/friend-request-dropdown.component";
import { AuthService } from "../../_services/auth.service";
import { UserSearchComponent } from '../search/search.component';
import { UserModelDTO } from '../../_models/usermodel.model';
import { PublicUserModel } from '../../shared/PublicUserModel';
import { ProfileService } from '../../_services/profile.service';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-top-navbar',
  standalone: true,
  templateUrl: './top-navbar.component.html',
  styleUrl: './top-navbar.component.css',
  imports: [
    RouterLink,
    NotificationDropdownComponent,
    FriendRequestDropdownComponent,
    UserSearchComponent,
    DefaultImageDirective
  ]
})
export class TopNavbarComponent implements OnInit, OnDestroy {
  usermodel: UserModelDTO | null = null;
  private userModelSubscription: Subscription | undefined;

  constructor(
    private authService: AuthService,
    private publicUserModel: PublicUserModel,
    private profileService: ProfileService
  ) {}

  ngOnInit() {
    this.getUserModel();
    this.userModelSubscription = this.publicUserModel.userModel$.subscribe(
      (user) => {
        this.usermodel = user;
      }
    );
  }

  ngOnDestroy() {
    if (this.userModelSubscription) {
      this.userModelSubscription.unsubscribe();
    }
  }

  signOut() {
    this.authService.logout();
  }

  getUserModel() {
    const info = this.authService.getUserInfoFromToken();
    if (info) {
      const username = info.username;
      this.profileService.getProfile(username).subscribe(
        (user: UserModelDTO) => {
          this.publicUserModel.setUserModel(user);
          console.log("User model set");
        },
        (error) => {
          console.error('Error getting user:', error);
        }
      );
    }
  }
}