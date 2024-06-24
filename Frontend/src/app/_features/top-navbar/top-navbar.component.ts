import {Component, OnInit} from '@angular/core';
import {RouterLink} from "@angular/router";
import {NotificationDropdownComponent} from "../notification-dropdown/notification-dropdown.component";
import {FriendRequestDropdownComponent} from "../friend-request-dropdown/friend-request-dropdown.component";
import {AuthService} from "../../_services/auth.service";
import { UserSearchComponent } from '../search/search.component';
import { UserModelDTO } from '../../_models/usermodel.model';
import { PublicUserModel } from '../../shared/PublicUserModel';

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
  constructor(private authService: AuthService,private publicUserModel:PublicUserModel) {
  }

  ngOnInit() {
  this.usermodel = this.publicUserModel.getUserModel();
  }

  signOut() {
    this.authService.logout();
  }

}
