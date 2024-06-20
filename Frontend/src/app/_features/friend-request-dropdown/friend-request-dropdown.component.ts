import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FriendRequestCardComponent } from "../friend-request-card/friend-request-card.component";
import { FriendRequest } from '../../_models/friend-request.model';
import { FriendRequestItemComponent } from "../friend-request-item/friend-request-item.component";
import { NgFor } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FriendRequestsService } from '../../_services/friend-request.service';

@Component({
    selector: 'app-friend-request-dropdown',
    standalone: true,
    templateUrl: './friend-request-dropdown.component.html',
    styleUrl: './friend-request-dropdown.component.css',
    imports: [RouterLink, FriendRequestCardComponent, FriendRequestItemComponent,NgFor]
})
export class FriendRequestDropdownComponent implements OnInit{
    friendRequests!: FriendRequest[];
    
    number!: number;

    constructor(private friendrequestService:FriendRequestsService){

    }
  ngOnInit(): void {
      this.loadFriendRequests();
  }

  loadFriendRequests() {
      this.friendrequestService.getFriendRequests().subscribe(
          (data) => {
              this.friendRequests = data;
              this.number = this.friendRequests.length;
          },
          (error) => {
              console.error('Error fetching friend requests:', error);
          }
      );
  }


}
