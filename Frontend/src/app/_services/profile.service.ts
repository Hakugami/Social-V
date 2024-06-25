import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { GatewayEnvironment } from "../../environments/gateway.environment";
import { UserModelDTO } from "../_models/usermodel.model";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class ProfileService {
    updateProfile(updatedUserData: Partial<UserModelDTO>): Observable<UserModelDTO> {
        return this.httpclient.put<UserModelDTO>(`${this.apiUrl}`, updatedUserData);
      }


    apiUrl = GatewayEnvironment.profileApiUrl;
  constructor(private httpclient:HttpClient) {

  }


    getProfile(username: string):Observable<UserModelDTO>{
        return this.httpclient.get<UserModelDTO>(`${this.apiUrl}/${username}`);
    }


    getProfilePosts(username: string){
        return this.httpclient.get(`http://localhost:3000/api/profile/${username}/posts`);
    }


    editProfile(username: string, profile: UserModelDTO){
        return this.httpclient.put(`http://localhost:3000/api/profile/${username}`, profile);
    }








}
