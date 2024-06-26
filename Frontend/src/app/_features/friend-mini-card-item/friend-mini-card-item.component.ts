import {Component, Input, OnInit} from '@angular/core';
import { UserModelDTO } from '../../_models/usermodel.model';
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { NgFor } from '@angular/common';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import {ActivatedRoute, Router} from '@angular/router';
import {ProfileService} from "../../_services/profile.service";
import {Subscription, switchMap} from "rxjs";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-friend-mini-card-item',
  standalone: true,
  imports: [NgFor,DefaultImageDirective],
  templateUrl: './friend-mini-card-item.component.html',
  styleUrl: './friend-mini-card-item.component.css'
})
export class FriendMiniCardItemComponent implements OnInit{
  friends:UserModelDTO[] = [];
  currentUser: string | null = null;
  username!: string | null;
  private subscription: Subscription | undefined;


  constructor(private friendService: FriendRequestsService, private router: Router,private profileService : ProfileService,
              private route : ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.route.paramMap.pipe(
      switchMap(params => {
        this.username = params.get('username');  // Add this line
        if (this.username) {
          return this.profileService.getProfile(this.username);
        }
        throw new Error('Username not provided');
      }),
      tap(user => {
        this.currentUser = user.email;
        this.getFriends();
      })).subscribe();

  }

  getFriends() {
    console.log(this.username+"-------------username----------------");
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

  navigateToFriendProfile(friendId: string): void {
    this.router.navigate(['/profile', friendId]);
  }

}
