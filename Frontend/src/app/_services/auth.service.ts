import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import  {jwtDecode} from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUrl = environment.authApiUrl;
  private userApiUrl = environment.userApiUrl;

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    return this.http.post(`${this.userApiUrl}/register`, user);
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.authBaseUrl}/login`, credentials);
  }
  checkFullName(fullName: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.userApiUrl}/checkFullName`, { params: { fullName } });
  }

  checkEmail(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.userApiUrl}/checkEmail`, { params: { email } });
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (token) {
      return !this.isTokenExpired(token);
    }
    return false;
  }

  getToken(): string | null {
    return localStorage.getItem('token'); // Adjust as per your storage mechanism
  }

  isTokenExpired(token: string): boolean {
    const decoded: any = jwtDecode(token);
    if (decoded.exp) {
      const expirationDate = new Date(0);
      expirationDate.setUTCSeconds(decoded.exp);
      console.log(expirationDate);
      return expirationDate < new Date();
    }
    return false;
  }

  getUsername(): string {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.username;
    }
    return '';
  }

  getEmail(): string {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.email;
    }
    return '';
  }

  getProfilePicUrl(): string {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.profilePicUrl;
    }
    return '';
  }


  logout(): void {
    localStorage.removeItem('token');
  }

  getUserInfoFromToken() {
    const token = this.getToken();
    if (token) {
      const decoded: any = jwtDecode(token);
      return {
        username: decoded.username,
        email: decoded.email
      };
    }
    return null;
  }
}
