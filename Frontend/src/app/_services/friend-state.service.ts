import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FriendRequest } from '../_models/friend-request.model';
import { UserModelDTO } from '../_models/usermodel.model';


@Injectable({
  providedIn: 'root'
})
export class FriendStateService {
  private friendRequestsSubject = new BehaviorSubject<FriendRequest[]>([]);
  private friendsSubject = new BehaviorSubject<UserModelDTO[]>([]);

  friendRequests$ = this.friendRequestsSubject.asObservable();
  friends$ = this.friendsSubject.asObservable();

  updateFriendRequests(requests: FriendRequest[]) {
    this.friendRequestsSubject.next(requests);
  }

  updateFriends(friends: UserModelDTO[]) {
    this.friendsSubject.next(friends);
  }

  removeFriendRequest(requestId: string) {
    const currentRequests = this.friendRequestsSubject.value;
    const updatedRequests = currentRequests.filter(r => r.id !== requestId);
    this.friendRequestsSubject.next(updatedRequests);
  }

  addFriend(friend: UserModelDTO) {
    const currentFriends = this.friendsSubject.value;
    this.friendsSubject.next([...currentFriends, friend]);
  }
}