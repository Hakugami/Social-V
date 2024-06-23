import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FriendRequestDTO } from '../_models/request-dto.model';
import { FriendRequest } from '../_models/friend-request.model';
import { UserModelDTO } from '../_models/userdto.model';
import {environment} from '../../environments/environment';

@Injectable({
providedIn: 'root'
})
export class FriendRequestsService {


constructor(private http: HttpClient) {}

  sendFriendRequest(request: FriendRequestDTO): Observable<void> {
    return this.http.post<void>(`${environment.friendApiUrl}/request`, request);
  }

  getFriendRequests(userId: string): Observable<FriendRequest[]> {
    // return this.http.get<FriendRequest[]>(`${environment.friendApiUrl}/request/${userId}`);
    return new Observable<FriendRequest[]>();
  }

  acceptFriendRequest(requestId: string): Observable<void> {
    // return this.http.post<void>(`${environment.friendApiUrl}/accept`, null, { params: { requestId } });
    return new Observable<void>();
  }

  getFriends(userId: string, page: number = 0, size: number = 10): Observable<UserModelDTO[]> {
    return this.http.get<UserModelDTO[]>(`${environment.friendApiUrl}/${userId}`, {
      params: { page: page.toString(), size: size.toString() }
    });
  }
}
