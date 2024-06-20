import { Component, Input } from '@angular/core';
import { FriendRequest } from '../../_models/friend-request.model';

@Component({
  selector: 'app-friend-request-card',
  standalone: true,
  imports: [],
  templateUrl: './friend-request-card.component.html',
  styleUrl: './friend-request-card.component.css'
})
export class FriendRequestCardComponent {
  
  @Input()
  request!: FriendRequest;

confirmRequest(arg0: FriendRequest) {
console.log(arg0);
}
deleteRequest(arg0: FriendRequest) {
console.log(arg0);
}
}
