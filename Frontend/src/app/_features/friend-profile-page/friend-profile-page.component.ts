import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostItemComponent } from "../post-item/post-item.component";

import { UserModelDTO } from '../../_models/usermodel.model';
import { FriendMiniCardItemComponent } from '../friend-mini-card-item/friend-mini-card-item.component';
import { PhotoMiniCardItemComponent } from '../photo-mini-card-item/photo-mini-card-item.component';
import { ProfileTimelineComponent } from '../profile-timeline/profile-timeline.component';
import { ProfileAboutComponent } from '../profile-about/profile-about.component';
import { ProfileFriendsComponent } from '../profile-friends/profile-friends.component';
import { ProfilePhotosComponent } from '../profile-photos/profile-photos.component';
import { FriendProfileHeaderComponent } from '../friend-profile-header/friend-profile-header.component';
import { ActivatedRoute } from '@angular/router';
import { ProfileService } from '../../_services/profile.service';

@Component({
  selector: 'app-friend-profile-page',
  standalone: true,
  imports: [
    CommonModule,
    PostItemComponent,
    FriendMiniCardItemComponent,
    PhotoMiniCardItemComponent,
    FriendProfileHeaderComponent,
    ProfileTimelineComponent,
    ProfileAboutComponent,
    ProfileFriendsComponent,
    ProfilePhotosComponent
  ],
  templateUrl: './friend-profile-page.component.html',
  styleUrl: './friend-profile-page.component.css'
})
export class FriendProfilePageComponent implements OnInit {
  @Input() friendUser!: UserModelDTO;

  constructor(
    private route: ActivatedRoute,
    private friendService: ProfileService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const friendId = params['username'];
      this.loadFriendData(friendId);
    });
  }

  private loadFriendData(friendId: string): void {
    this.friendService.getProfile(friendId).subscribe(
      (data: UserModelDTO) => {
        this.friendUser = data;
        console.log('Friend data:', data);
      },
      error => {
        console.error('Error fetching friend data:', error);
      }
    );
  }
}