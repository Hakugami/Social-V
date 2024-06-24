import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { FriendRequest } from '../_models/friend-request.model';

@Injectable({
  providedIn: 'root'
})
export class SharedFriendRequestService {
  private friendRequestsSubject = new BehaviorSubject<FriendRequest[]>([]);
  friendRequests$ = this.friendRequestsSubject.asObservable();

  updateFriendRequests(requests: FriendRequest[]) {
    this.friendRequestsSubject.next(requests);
  }

  removeFriendRequest(requestId: string) {
    const currentRequests = this.friendRequestsSubject.value;
    const updatedRequests = currentRequests.filter(r => r.id !== requestId);
    this.friendRequestsSubject.next(updatedRequests);
  }
}
