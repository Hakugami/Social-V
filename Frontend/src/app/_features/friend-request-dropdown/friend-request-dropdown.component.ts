import {Component, OnDestroy, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {FriendRequestCardComponent} from "../friend-request-card/friend-request-card.component";
import {FriendRequestItemComponent} from "../friend-request-item/friend-request-item.component";
import {NgFor} from '@angular/common';
import {FriendRequestsService} from '../../_services/friend-request.service';
import {AuthService} from '../../_services/auth.service'; // Assuming you have this service
import {FriendRequest} from '../../_models/friend-request.model';
import {SharedFriendRequestService} from "../../_services/shared-friend-request.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-friend-request-dropdown',
  standalone: true,
  templateUrl: './friend-request-dropdown.component.html',
  styleUrl: './friend-request-dropdown.component.css',
  imports: [RouterLink, FriendRequestCardComponent, FriendRequestItemComponent, NgFor]
})
export class FriendRequestDropdownComponent implements OnInit, OnDestroy {
  friendRequests: FriendRequest[] = [];
  number: number = 0;
  subscription: Subscription | undefined;


  constructor(
    private friendRequestService: FriendRequestsService,
    private authService: AuthService,
    private sharedFriendRequestService: SharedFriendRequestService
  ) {
  }

  ngOnInit(): void {
    this.loadFriendRequests();
    this.subscription = this.sharedFriendRequestService.friendRequests$.subscribe(
      requests => {
        this.friendRequests = requests;
        this.number = this.friendRequests.length;
      }
    );
  }

  loadFriendRequests() {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo && userInfo.email) {
      this.friendRequestService.getFriendRequests(userInfo.email).subscribe(
        (data) => {
          this.friendRequests = data;
          this.number = this.friendRequests.length;
          this.sharedFriendRequestService.updateFriendRequests(this.friendRequests);
        },
        (error) => {
          console.error('Error fetching friend requests:', error);
        }
      );
    } else {
      console.error('User email not found in token');
    }
  }

  confirmRequest(request: FriendRequest) {
    this.friendRequestService.acceptFriendRequest(request.id).subscribe(
      () => {
        this.sharedFriendRequestService.removeFriendRequest(request.id);
        this.friendRequests = this.friendRequests.filter(r => r.id !== request.id);
        this.number = this.friendRequests.length;
        console.log('Friend request accepted:', request);
      },
      (error) => {
        console.error('Error accepting friend request:', error);
      }
    );
  }

  deleteRequest(request: FriendRequest) {
    // Implement deletion logic here
    this.friendRequestService.deleteFriendRequest(request.id).subscribe(
      () => {
        this.sharedFriendRequestService.removeFriendRequest(request.id);
        this.friendRequests = this.friendRequests.filter(r => r.id !== request.id);
        this.number = this.friendRequests.length;
        console.log('Friend request deleted:', request);
      },
      (error) => {
        console.error('Error deleting friend request:', error);
      }
    );
    console.log('Delete request:', request);

  }

  ngOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
