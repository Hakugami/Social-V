import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FriendRequest } from '../../_models/friend-request.model';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import { AuthService } from '../../_services/auth.service';
import { FriendRequestsService } from '../../_services/friend-request.service';

@Component({
  selector: 'app-friend-request-card',
  standalone: true,
  imports: [DefaultImageDirective],
  templateUrl: './friend-request-card.component.html',
  styleUrl: './friend-request-card.component.css'
})
export class FriendRequestCardComponent {
  constructor(private authService:AuthService,private friendRequestService:FriendRequestsService) { }

  @Input()
  request!: FriendRequest;
  @Output() requestHandled = new EventEmitter<string>();

confirmRequest(request: FriendRequest) {
  this.friendRequestService.acceptFriendRequest(request.id).subscribe(
    (data) => {
      console.log('Friend request confirmed:', data);
      this.requestHandled.emit(request.id);

    },
    (error) => {
      console.error('Error confirming friend request:', error);
    }
  );

}
deleteRequest(request: FriendRequest) {
  this.friendRequestService.deleteFriendRequest(request.id).subscribe(
    (data) => {
      console.log('Friend request deleted:', request.id);
      this.requestHandled.emit(request.id);

    },
    (error) => {
      console.error('Error deleting friend request:', error);
    }
  );

}
}
