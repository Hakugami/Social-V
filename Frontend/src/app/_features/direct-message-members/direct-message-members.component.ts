import {Component, EventEmitter, OnInit, OnDestroy, Output, ChangeDetectorRef, ChangeDetectionStrategy} from '@angular/core';
import {NgClass, NgForOf} from "@angular/common";
import {SharedFriendRequestService} from "../../_services/shared-friend-request.service";
import {PublicUserModel} from "../../shared/PublicUserModel";
import {UserModelDTO} from "../../_models/usermodel.model";
import {DefaultImageDirective} from "../../_directives/default-image.directive";
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-direct-message-members',
  standalone: true,
  imports: [
    NgForOf,
    NgClass,
    DefaultImageDirective
  ],
  templateUrl: './direct-message-members.component.html',
  styleUrl: './direct-message-members.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DirectMessageMembersComponent implements OnInit, OnDestroy {
  directMessages: UserModelDTO[] = [];
  currentUser: UserModelDTO | null = null;
  private friendsSubscription: Subscription | undefined;

  @Output() userSelected = new EventEmitter<string>();

  constructor(
    private sharedFriendRequestService: SharedFriendRequestService,
    private publicUserModel: PublicUserModel,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.currentUser = this.publicUserModel.getUserModel();
    this.subscribeToFriends();
  }

  ngOnDestroy(): void {
    if (this.friendsSubscription) {
      this.friendsSubscription.unsubscribe();
    }
  }

  private subscribeToFriends(): void {
    this.friendsSubscription = this.sharedFriendRequestService.friends$.subscribe(friends => {
      if (this.haveFriendsChanged(friends)) {
        this.directMessages = friends;
        this.cdr.detectChanges();
      }
    });
  }

  private haveFriendsChanged(newFriends: UserModelDTO[]): boolean {
    if (this.directMessages.length !== newFriends.length) return true;
    return JSON.stringify(this.directMessages) !== JSON.stringify(newFriends);
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'online':
        return 'text-success';
      case 'offline':
        return 'text-dark';
      case 'away':
        return 'text-warning';
      default:
        return 'text-dark';
    }
  }

  selectUser(username: string) {
    this.userSelected.emit(username);
    console.log("direct-message-members.component.ts: selectUser: username: ", username);
  }

  trackByFn(index: number, friend: UserModelDTO): string {
    return friend.username || friend.email || index.toString();
  }
}
