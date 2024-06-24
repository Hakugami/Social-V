import { Component } from '@angular/core';
import { UserModelDTO } from '../../_models/usermodel.model';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { NgFor } from '@angular/common';
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-friend-mini-card-item',
  standalone: true,
  imports: [NgFor,DefaultImageDirective],
  templateUrl: './friend-mini-card-item.component.html',
  styleUrl: './friend-mini-card-item.component.css'
})
export class FriendMiniCardItemComponent {
  friends:UserModelDTO[] = [];
  currentUser: string | null = null;

  constructor(private friendService: FriendRequestsService, private authService: AuthService) {
    const userInfo = this.authService.getUserInfoFromToken();
    this.currentUser = userInfo?.email || null;
  }

  ngOnInit() {
    this.getFriends();
  }

  getFriends() {
    if (this.currentUser) {
      this.friendService.getFriends(this.currentUser).subscribe(
        (friends: UserModelDTO[]) => {
          this.friends = friends;
        },
        (error) => {
          console.error('Error getting friends:', error);
        }
      );
    }
  }

}
