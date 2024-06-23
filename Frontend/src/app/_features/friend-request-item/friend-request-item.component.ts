import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FriendRequest } from '../../_models/friend-request.model';

@Component({
  selector: 'app-friend-request-item',
  templateUrl: './friend-request-item.component.html',
  styleUrls: ['./friend-request-item.component.css'],
  standalone: true
})
export class FriendRequestItemComponent {
  @Input() friendRequest!: FriendRequest;
  @Output() confirm = new EventEmitter<FriendRequest>();
  @Output() delete = new EventEmitter<FriendRequest>();

  onConfirm() {
    this.confirm.emit(this.friendRequest);
  }

  onDelete() {
    this.delete.emit(this.friendRequest);
  }
}