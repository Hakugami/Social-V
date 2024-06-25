import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { FriendRequestDTO } from '../_models/request-dto.model';
import { FriendRequest } from '../_models/friend-request.model';
import { UserModelDTO } from '../_models/userdto.model';
import {environment} from '../../environments/environment';

@Injectable({
providedIn: 'root'
})
export class FriendRequestsService {
  private friendSource = new BehaviorSubject<UserModelDTO[]>([]);
  friends$ = this.friendSource.asObservable();


  updateFriends(friends: UserModelDTO[]) {
    this.friendSource.next(friends);
  }


  deleteFriendRequest(id: string) :Observable<void> {
    return this.http.delete<void>(`${environment.friendApiUrl}/request/${id}`);
  }


constructor(private http: HttpClient) {}

  sendFriendRequest(request: FriendRequestDTO): Observable<void> {
    return this.http.post<void>(`${environment.friendApiUrl}/request`, request);
  }

  getFriendRequests(userId: string): Observable<FriendRequest[]> {
    return this.http.get<FriendRequest[]>(`${environment.friendApiUrl}/request/${userId}`);

  }

  acceptFriendRequest(requestId: string): Observable<void> {
    return this.http.post<void>(`${environment.friendApiUrl}/accept`, null, { params: { requestId } });
  }

  getFriends(userId: string, page: number = 0, size: number = 10): Observable<UserModelDTO[]> {
    return this.http.get<UserModelDTO[]>(`${environment.friendApiUrl}/${userId}`, {
      params: { page: page.toString(), size: size.toString() }
    });
  }

  getPendingFriendRequests(userId: string): Observable<FriendRequestDTO[]> {
    return this.http.get<FriendRequestDTO[]>(`${environment.friendApiUrl}/pending/${userId}`);
  }

}
