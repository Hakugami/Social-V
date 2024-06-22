import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {CreatePostModel} from "../_models/create-post.model";

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiBaseUrl: string = environment.postApiUrl;

  constructor(private httpClient: HttpClient) {
  }

  createTextPost(post: CreatePostModel): Observable<CreatePostModel> {
    return this.httpClient.post<CreatePostModel>(`${this.apiBaseUrl}/text`, post);
  }

  createImagePost(postData: FormData): Observable<HttpEvent<any>> {
    const req = new HttpRequest('POST', `${this.apiBaseUrl}/image`, postData, {
      reportProgress: true
    });
    return this.httpClient.request(req);
  }

  createVideoPost(postData: FormData): Observable<HttpEvent<any>> {
    const req = new HttpRequest('POST', `${this.apiBaseUrl}/video`, postData, {
      reportProgress: true
    });
    return this.httpClient.request(req);
  }

  getPosts(): Observable<any> {
    // return this.httpClient.get<any>(`${this.apiBaseUrl}`);
    //return empty array for now
    return new Observable<any>();
  }
}
