import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NotificationDropdownComponent} from "../notification-dropdown/notification-dropdown.component";
import {FriendRequestDropdownComponent} from "../friend-request-dropdown/friend-request-dropdown.component";
import {AuthService} from "../../_services/auth.service";
import { UserSearchComponent } from '../search/search.component';
import { UserModelDTO } from '../../_models/usermodel.model';
import { PublicUserModel } from '../../shared/PublicUserModel';
import { ProfileService } from '../../_services/profile.service';

@Component({
    selector: 'app-top-navbar',
    standalone: true,
    templateUrl: './top-navbar.component.html',
    styleUrl: './top-navbar.component.css',
    imports: [
        RouterLink,
        NotificationDropdownComponent,
        FriendRequestDropdownComponent,
        UserSearchComponent
    ]
})
export class TopNavbarComponent implements OnInit{
  usermodel:UserModelDTO | undefined;
  constructor(private authService: AuthService,private publicUserModel:PublicUserModel,private profileService:ProfileService) {
  }

  ngOnInit() {
  this.getUserModel();
  // this.usermodel = this.publicUserModel.getUserModel();
  }

  signOut() {
    this.authService.logout();
  }

  getUserModel(){
    const info =this.authService.getUserInfoFromToken();
    if(info){
       const username=info.username;
        this.profileService.getProfile(username).subscribe(
          (user:UserModelDTO) => {
            PublicUserModel.user_model = user;
            console.log(user);
            console.log(PublicUserModel.user_model);
            console.log("User model set");
          },
          (error) => {
            console.error('Error getting user:', error);
          }
        );
      }
    }

}
