import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FriendRequestDTO } from '../_models/request-dto.model';
import { FriendRequest } from '../_models/friend-request.model';

import {GatewayEnvironment} from "../../environments/gateway.environment";
import { UserModelDTO } from '../_models/usermodel.model';


@Injectable({
providedIn: 'root'
})
export class FriendRequestsService {

  removeFriend(currentUser: string, email: string) {
    return this.http.delete<void>(`${GatewayEnvironment.friendApiUrl}/${currentUser}?friendId=${email}`);
  }
  deleteFriendRequest(id: string) :Observable<void> {
    return this.http.delete<void>(`${GatewayEnvironment.friendApiUrl}/request/${id}`);
  }


constructor(private http: HttpClient) {}

  sendFriendRequest(request: FriendRequestDTO): Observable<void> {
    return this.http.post<void>(`${GatewayEnvironment.friendApiUrl}/request`, request);
  }

  getFriendRequests(userId: string): Observable<FriendRequest[]> {
    return this.http.get<FriendRequest[]>(`${GatewayEnvironment.friendApiUrl}/request/${userId}`);

  }

  acceptFriendRequest(requestId: string): Observable<void> {
    return this.http.post<void>(`${GatewayEnvironment.friendApiUrl}/accept`, null, { params: { requestId } });
  }

  getFriends(userId: string, page: number = 0, size: number = 10): Observable<UserModelDTO[]> {
    return this.http.get<UserModelDTO[]>(`${GatewayEnvironment.friendApiUrl}/${userId}`, {
      params: { page: page.toString(), size: size.toString() }
    });
  }

  getPendingFriendRequests(userId: string): Observable<FriendRequestDTO[]> {
    return this.http.get<FriendRequestDTO[]>(`${GatewayEnvironment.friendApiUrl}/pending/${userId}`);
  }

}
