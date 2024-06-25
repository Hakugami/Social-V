import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {GatewayEnvironment} from "../../environments/gateway.environment";
import {UserModelDTO} from "../_models/usermodel.model";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class ProfileService {
  updateProfile(updatedUserData: Partial<UserModelDTO>): Observable<UserModelDTO> {
    return this.httpclient.put<UserModelDTO>(`${this.apiUrl}`, updatedUserData);
  }


  apiUrl = GatewayEnvironment.profileApiUrl;

  constructor(private httpclient: HttpClient) {

  }


  getProfile(username: string): Observable<UserModelDTO> {
    return this.httpclient.get<UserModelDTO>(`${this.apiUrl}/${username}`);
  }


}
