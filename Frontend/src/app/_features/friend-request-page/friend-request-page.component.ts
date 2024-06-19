import { Component } from '@angular/core';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { FriendRequest } from '../../_models/friend-request.model';
import { FriendRequestCardComponent } from "../friend-request-card/friend-request-card.component";
import { NgFor } from '@angular/common';

@Component({
    selector: 'app-friend-request-page',
    standalone: true,
    templateUrl: './friend-request-page.component.html',
    styleUrl: './friend-request-page.component.css',
    imports: [FriendRequestCardComponent,NgFor]
})
export class FriendRequestPageComponent {
  friendRequests: FriendRequest[] = [];

  constructor(private friendRequestsService: FriendRequestsService) { }

  ngOnInit(): void {
      this.loadFriendRequests();
  }

  loadFriendRequests() {
      this.friendRequestsService.getFriendRequests().subscribe(
          (data) => {
              this.friendRequests = data;
          },
          (error) => {
              console.error('Error fetching friend requests:', error);
          }
      );
  }

  confirmRequest(request: FriendRequest) {
      // Implement confirmation logic here
      console.log('Confirm request:', request);
  }

  deleteRequest(request: FriendRequest) {
      // Implement deletion logic here
      console.log('Delete request:', request);
  }

  loadMoreRequests() {
      // Implement loading more requests logic here
      console.log('Load more requests');
  }
}
