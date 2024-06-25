import { Component, Input, OnInit } from '@angular/core';
import {PostItemComponent} from "../post-item/post-item.component";
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {ProfileHeaderComponent} from "../profile-header/profile-header.component";
import {ProfileTimelineComponent} from "../profile-timeline/profile-timeline.component";
import {ProfileAboutComponent} from "../profile-about/profile-about.component";
import {ProfileFriendsComponent} from "../profile-friends/profile-friends.component";
import {ProfilePhotosComponent} from "../profile-photos/profile-photos.component";
import { UserModelDTO } from '../../_models/usermodel.model';
import { ProfileService } from '../../_services/profile.service';
import { AuthService } from '../../_services/auth.service';
import { PublicUserModel } from '../../shared/PublicUserModel';
import { Subscription } from 'rxjs';

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
export class ProfilePageComponent implements OnInit{
  constructor(    private publicUserModel: PublicUserModel) {

   }
   user: UserModelDTO | null = null;
   private userModelSubscription: Subscription | undefined;

  ngOnInit(): void {

   this.userModelSubscription = this.publicUserModel.userModel$.subscribe(
      (user) => {
        this.user = user;
      }
    );

  }





}
