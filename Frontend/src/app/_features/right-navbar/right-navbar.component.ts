import { Component, OnInit } from '@angular/core';
import { Friend } from '../../_models/friend.model';
import { NgFor } from '@angular/common';
import { FriendStatusComponent } from "../friend-status/friend-status.component";
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { UserModelDTO } from '../../_models/userdto.model';

@Component({
    selector: 'app-right-navbar',
    standalone: true,
    templateUrl: './right-navbar.component.html',
    styleUrl: './right-navbar.component.css',
    imports: [NgFor, FriendStatusComponent]
})
export class RightNavbarComponent implements OnInit{
  constructor(
    private friendRequestsService: FriendRequestsService,
    private authService: AuthService
) { }

  ngOnInit(): void {
    this.getFriends();
  }
  getFriends() {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo && userInfo.email) {
        this.friendRequestsService.getFriends(userInfo.email).subscribe(
            (data) => {
                this.friends = data;
            },
            (error) => {
                console.error('Error fetching friends:', error);
            }
        );
    } else {
        console.error('User email not found in token');
    }
  }
  friends!: UserModelDTO[];


}
