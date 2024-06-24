import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserModelDTO } from '../_models/userdto.model';
import {GatewayEnvironment} from "../../environments/gateway.environment";

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  private apiUrl = GatewayEnvironment.recommendApiUrl;

  constructor(private http: HttpClient) {}

  getRecommendations(userId: string, limit: number): Observable<UserModelDTO[]> {
    return this.http.get<UserModelDTO[]>(`${this.apiUrl}/recommendations`, {
      params: { userId, limit: limit.toString() }
    });
  }

  getSecondDegreeConnections(userId: string, limit: number): Observable<UserModelDTO[]> {
    return this.http.get<UserModelDTO[]>(`${this.apiUrl}/second-degree-connections`, {
      params: { userId, limit: limit.toString() }
    });
  }
}
