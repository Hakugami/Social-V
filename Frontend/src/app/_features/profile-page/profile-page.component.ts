import {Component, OnDestroy, OnInit} from '@angular/core';
import {PostItemComponent} from "../post-item/post-item.component";
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {ProfileHeaderComponent} from "../profile-header/profile-header.component";
import {ProfileTimelineComponent} from "../profile-timeline/profile-timeline.component";
import {ProfileAboutComponent} from "../profile-about/profile-about.component";
import {ProfileFriendsComponent} from "../profile-friends/profile-friends.component";
import {ProfilePhotosComponent} from "../profile-photos/profile-photos.component";
import {UserModelDTO} from '../../_models/usermodel.model';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from "@angular/router";
import {switchMap, tap} from "rxjs/operators";
import {ProfileService} from "../../_services/profile.service";
import {AuthService} from "../../_services/auth.service";

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
export class ProfilePageComponent implements OnInit, OnDestroy {
  constructor(
    private profileService: ProfileService,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
  }

  user: UserModelDTO | null = null;
  private subscription: Subscription | undefined;
  isOwnProfile: boolean = false;
  username: string | null = null;  // Add this line

  ngOnInit(): void {
    this.subscription = this.route.paramMap.pipe(
      switchMap(params => {
        this.username = params.get('username');  // Add this line
        if (this.username) {
          return this.profileService.getProfile(this.username);
        }
        throw new Error('Username not provided');
      }),
      tap(user => {
        this.user = user;
        this.checkIfOwnProfile();
      })
    ).subscribe(
      () => {
      },
      error => console.error('Error fetching user profile:', error)
    );
  }

  checkIfOwnProfile(): void {
    const currentUsername = this.authService.getUsername();
    this.isOwnProfile = currentUsername === this.user?.username;
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
