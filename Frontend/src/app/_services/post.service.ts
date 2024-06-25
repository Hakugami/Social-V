import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {CreatePostModel} from "../_models/create-post.model";
import {PostModel} from "../_models/post.model";
import {LikeRequestModel} from "../_models/like-request.model";
import {LikesModel} from "../_models/likes.model";
import {CommentRequestModel} from "../_models/comment-request.model";
import {GatewayEnvironment} from "../../environments/gateway.environment";


@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiBaseUrl: string = GatewayEnvironment.postApiUrl;
  private likeApiBaseUrl: string = GatewayEnvironment.likeApiUrl;
  private commentApiBaseUrl: string = GatewayEnvironment.commentApiUrl;

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

  getPosts(): Observable<PostModel[]> {
    return this.httpClient.get<PostModel[]>(`${this.apiBaseUrl}/`,);
  }

  likePost(likeRequestModel: LikeRequestModel): Observable<LikesModel> {
    return this.httpClient.post<LikesModel>(`${this.likeApiBaseUrl}/`, likeRequestModel);
  }

  removeLike(likeId: string): Observable<LikesModel> {
    return this.httpClient.delete<LikesModel>(`${this.likeApiBaseUrl}/${likeId}`);
  }

  addComment(commentRequest: CommentRequestModel ): Observable<any> {
    return this.httpClient.post<any>(`${this.commentApiBaseUrl}/`, commentRequest);

  }

  updateLike(likeId: string, emotion: string): Observable<LikesModel> {
    return this.httpClient.put<LikesModel>(`${this.likeApiBaseUrl}/${likeId}`, {emotion});
  }

  getPostById(userId: string): Observable<PostModel[]> {
    return this.httpClient.get<PostModel[]>(`${this.apiBaseUrl}/user/${userId}`);
  }
}
