import { Component } from '@angular/core';
import {FriendItemComponent} from "../friend-item/friend-item.component";

@Component({
  selector: 'app-profile-friends',
  standalone: true,
  imports: [
    FriendItemComponent
  ],
  templateUrl: './profile-friends.component.html',
  styleUrl: './profile-friends.component.css'
})
export class ProfileFriendsComponent {

}
