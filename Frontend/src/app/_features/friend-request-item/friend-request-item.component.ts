import { Component, Input } from '@angular/core';
import { FriendRequest } from '../../_models/friend-request.model';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-friend-request-item',
  standalone: true,
  imports: [NgFor],
  templateUrl: './friend-request-item.component.html',
  styleUrl: './friend-request-item.component.css'
})
export class FriendRequestItemComponent {
@Input()
friendRequest!: FriendRequest;
}
