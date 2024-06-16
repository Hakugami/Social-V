import { Component } from '@angular/core';
import {PostItemComponent} from "../post-item/post-item.component";
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {ProfileHeaderComponent} from "../profile-header/profile-header.component";
import {ProfileTimelineComponent} from "../profile-timeline/profile-timeline.component";
import {ProfileAboutComponent} from "../profile-about/profile-about.component";
import {ProfileFriendsComponent} from "../profile-friends/profile-friends.component";
import {ProfilePhotosComponent} from "../profile-photos/profile-photos.component";

@Component({
  selector: 'app-profile-page',
  standalone: true,
  imports: [
    PostItemComponent,
    CreatePostComponent,
    FriendMiniCardItemComponent,
    PhotoMiniCardItemComponent,
    ProfileHeaderComponent,
    ProfileTimelineComponent,
    ProfileAboutComponent,
    ProfileFriendsComponent,
    ProfilePhotosComponent
  ],
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {

}
