import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  EventEmitter,
  Output
} from '@angular/core';
import { interval, Subscription } from 'rxjs';
import { startWith, switchMap } from 'rxjs/operators';
import { Friend } from '../../_models/friend.model';
import { NgFor } from '@angular/common';
import { FriendStatusComponent } from "../friend-status/friend-status.component";
import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { UserModelDTO } from "../../_models/usermodel.model";
import { SharedFriendRequestService } from '../../_services/shared-friend-request.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-right-navbar',
  standalone: true,
  templateUrl: './right-navbar.component.html',
  styleUrl: './right-navbar.component.css',
  imports: [NgFor, FriendStatusComponent],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RightNavbarComponent implements OnInit, OnDestroy {
  friends: UserModelDTO[] = [];
  private friendsSubscription: Subscription | undefined;
  private pollingSubscription: Subscription | undefined;

  @Output() friendSelected = new EventEmitter<string>();

  onFriendClicked(username: string) {
    this.router.navigate(['/chat', username]);

  }

  constructor(
    private friendRequestsService: FriendRequestsService,
    private authService: AuthService,
    private sharedFriendRequestService: SharedFriendRequestService,
    private cdr: ChangeDetectorRef,
    private router : Router
  ) { }

  ngOnInit(): void {
    this.setupFriendsSubscription();
    this.setupPolling();
  }

  setupFriendsSubscription() {
    this.friendsSubscription = this.sharedFriendRequestService.friends$.subscribe(
      (friends) => {
        if (this.haveFriendsChanged(friends)) {
          this.friends = friends;
          this.cdr.detectChanges();
        }
      }
    );
  }

  setupPolling() {
    this.pollingSubscription = interval(30000).pipe(
      startWith(0),
      switchMap(() => this.getFriends())
    ).subscribe();
  }

  getFriends() {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo && userInfo.email) {
      return this.friendRequestsService.getFriends(userInfo.email).pipe(
        switchMap(data => {
          if (this.haveFriendsChanged(data)) {
            this.sharedFriendRequestService.updateFriends(data);
          }
          return [];
        })
      );
    } else {
      console.error('User email not found in token');
      return [];
    }
  }

  haveFriendsChanged(newFriends: UserModelDTO[]): boolean {
    if (this.friends.length !== newFriends.length) return true;
    return JSON.stringify(this.friends) !== JSON.stringify(newFriends);
  }

  trackByFn(index: number, friend: UserModelDTO): string {
    return friend.username|| friend.email || index.toString();
  }

  ngOnDestroy() {
    if (this.friendsSubscription) {
      this.friendsSubscription.unsubscribe();
    }
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }
}
