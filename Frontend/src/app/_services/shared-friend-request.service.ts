import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FriendRequest } from '../_models/friend-request.model';
import {UserModelDTO} from "../_models/usermodel.model";

@Injectable({
  providedIn: 'root'
})
export class SharedFriendRequestService {
  private friendRequestsSubject = new BehaviorSubject<FriendRequest[]>([]);
  friendRequests$ = this.friendRequestsSubject.asObservable();

  private friendsSubject = new BehaviorSubject<UserModelDTO[]>([]);
  friends$ = this.friendsSubject.asObservable();

  updateFriendRequests(requests: FriendRequest[]) {
    this.friendRequestsSubject.next(requests);
  }

  removeFriendRequest(requestId: string) {
    const currentRequests = this.friendRequestsSubject.value;
    const updatedRequests = currentRequests.filter(r => r.id !== requestId);
    this.friendRequestsSubject.next(updatedRequests);
  }

  updateFriends(friends: UserModelDTO[]) {
    this.friendsSubject.next(friends);
  }

  addFriend(friend: UserModelDTO) {
    const currentFriends = this.friendsSubject.value;
    this.friendsSubject.next([...currentFriends, friend]);
  }

  removeFriend(email: string) {
    const currentFriends = this.friendsSubject.value;
    const updatedFriends = currentFriends.filter(f => f.email !== email);
    this.friendsSubject.next(updatedFriends);
  }

}
