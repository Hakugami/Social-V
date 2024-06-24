import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { UserModelDTO } from '../../_models/userdto.model';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { FriendRequestDTO } from '../../_models/request-dto.model';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import { CommonModule, NgIf } from '@angular/common';

@Component({
  selector: 'app-person-card',
  templateUrl: './person-card.component.html',
  styleUrls: ['./person-card.component.css'],
  standalone: true,
  imports: [DefaultImageDirective, CommonModule,NgIf]
})
export class PersonCardComponent implements OnInit {
  @Input() person!: UserModelDTO;
  @Output() friendRequestSent = new EventEmitter<string>();
  @Output() friendRemoved = new EventEmitter<string>();

  currentUser: string | null = null;
  isFriend: boolean = false;
  isPending: boolean = false;

  constructor(private friendService: FriendRequestsService, private authService: AuthService) {
    const userInfo = this.authService.getUserInfoFromToken();
    this.currentUser = userInfo?.email || null;
  }

  ngOnInit() {
    if (!this.isCurrentUser()) {
      this.checkFriendStatus();
      this.checkPendingStatus();
    }
  }

  checkFriendStatus() {
    if (this.currentUser) {
      this.friendService.getFriends(this.currentUser).subscribe(
        (friends: UserModelDTO[]) => {
          this.isFriend = friends.some(friend => friend.email === this.person.email);
        },
        (error) => {
          console.error('Error checking friend status:', error);
        }
      );
    }
  }

  checkPendingStatus() {
    if (this.currentUser) {
      this.friendService.getPendingFriendRequests(this.currentUser).subscribe(
        (pendingRequests: FriendRequestDTO[]) => {
          this.isPending = pendingRequests.some(request => request.recipientId === this.person.email);
        },
        (error) => {
          console.error('Error checking pending requests:', error);
        }
      );
    }
  }

  addFriend(): void {
    if (this.currentUser) {
      const friendRequest: FriendRequestDTO = {
        requesterId: this.currentUser,
        recipientId: this.person.email
      };
      this.friendService.sendFriendRequest(friendRequest).subscribe(
        (data) => {
          console.log('Friend request sent:', data);
          this.isPending = true;
          this.friendRequestSent.emit(this.person.email);
        },
        (error) => {
          console.error('Error sending friend request:', error);
        }
      );
    }
  }

  removeFriend(): void {
    if (this.currentUser) {
      this.friendService.removeFriend(this.currentUser, this.person.email).subscribe(
        (data) => {
          console.log('Friend removed:', data);
          this.isFriend = false;
          this.friendRemoved.emit(this.person.email);
        },
        (error) => {
          console.error('Error removing friend:', error);
        }
      );
    }
  }

  removeSuggestion(): void {
    this.friendRequestSent.emit(this.person.email);
  }

  isCurrentUser(): boolean {
    return this.currentUser === this.person.email;
  }
}
