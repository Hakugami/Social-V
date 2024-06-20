import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Friend } from '../../_models/friend.model';

@Component({
  selector: 'app-friend-status',
  standalone: true,
  imports: [NgClass],
  templateUrl: './friend-status.component.html',
  styleUrl: './friend-status.component.css'
})
export class FriendStatusComponent {
@Input()
status: string='status-online';
  @Input()
  friend!: Friend;

}
