import { Component, Input, Output, EventEmitter } from '@angular/core';
import { UserModelDTO } from '../../_models/userdto.model';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { FriendRequestDTO } from '../../_models/request-dto.model';
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-person-card',
  templateUrl: './person-card.component.html',
  styleUrls: ['./person-card.component.css'],
  standalone: true,
  imports: [DefaultImageDirective]
})
export class PersonCardComponent {
  @Input() person!: UserModelDTO;
  @Output() friendRequestSent = new EventEmitter<string>();

  constructor(private friendService: FriendRequestsService, private authService: AuthService) {}

  addFriend(): void {
    if (this.authService.getUserInfoFromToken() != null) {
      const userInfo = this.authService.getUserInfoFromToken();
      if (userInfo != null) {
        const userId: string = userInfo.email;
        const friendRequest: FriendRequestDTO = {
          requesterId: userId,
          recipientId: this.person.email
        };

        this.friendService.sendFriendRequest(friendRequest).subscribe(
          (data) => {
            console.log('Friend request sent:', data);
            this.friendRequestSent.emit(this.person.email);
          },
          (error) => {
            console.error('Error sending friend request:', error);
          }
        );
      }
    }
  }

  removeSuggestion(): void {
    this.friendRequestSent.emit(this.person.email);
  }
}