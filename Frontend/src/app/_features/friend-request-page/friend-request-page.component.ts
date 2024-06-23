import { Component, OnInit } from '@angular/core';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { FriendRequest } from '../../_models/friend-request.model';
import { FriendRequestCardComponent } from "../friend-request-card/friend-request-card.component";
import { NgFor } from '@angular/common';
import { AuthService } from '../../_services/auth.service';
import { PeopleYouMayKnowComponent } from "../people-you-might-know/people-you-might-know.component";

@Component({
    selector: 'app-friend-request-page',
    standalone: true,
    templateUrl: './friend-request-page.component.html',
    styleUrl: './friend-request-page.component.css',
    imports: [FriendRequestCardComponent, NgFor, PeopleYouMayKnowComponent]
})
export class FriendRequestPageComponent implements OnInit {
    friendRequests: FriendRequest[] = [];
    currentPage = 0;
    pageSize = 20; // Larger page size for this component
    hasMoreRequests = true;

    constructor(
        private friendRequestsService: FriendRequestsService,
        private authService: AuthService
    ) { }

    ngOnInit(): void {
        this.loadFriendRequests();
    }

    loadFriendRequests(loadMore: boolean = false) {
        if (loadMore) {
            this.currentPage++;
        } else {
            this.currentPage = 0;
            this.friendRequests = [];
        }

        const userInfo = this.authService.getUserInfoFromToken();
        if (userInfo && userInfo.email) {
            this.friendRequestsService.getFriendRequests(userInfo.email).subscribe(
                (data) => {
                    if (loadMore) {
                        this.friendRequests = [...this.friendRequests, ...data];
                    } else {
                        this.friendRequests = data;
                    }
                    this.hasMoreRequests = data.length === this.pageSize;
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
        this.friendRequestsService.acceptFriendRequest(request.id).subscribe(
            () => {
                // Remove the accepted request from the list
                this.friendRequests = this.friendRequests.filter(r => r.id !== request.id);
                console.log('Friend request accepted:', request);
            },
            (error) => {
                console.error('Error accepting friend request:', error);
            }
        );
    }


    loadMoreRequests() {
        if (this.hasMoreRequests) {
            this.loadFriendRequests(true);
        }
    }

    onRequestHandled(requestId: string) {
        console.log('Request handled: delete the card ', requestId);
        this.friendRequests = this.friendRequests.filter(request => request.id !== requestId);
      }
}