import { Injectable } from '@angular/core';
import { Subject, interval, Subscription } from 'rxjs';
import { startWith, switchMap } from 'rxjs/operators';
import { PostService } from './post.service';
import {PostModel} from "../_models/post.model";

@Injectable({
  providedIn: 'root'
})
export class PostUpdateService {
  private postCreatedSource = new Subject<void>();
  private pollingSubscription: Subscription | null = null;

  postCreated$ = this.postCreatedSource.asObservable();

  constructor(private postService: PostService) {}

  notifyPostCreated() {
    this.postCreatedSource.next();
  }

  startPolling(pollingInterval: number = 30000) {
    if (this.pollingSubscription) {
      this.stopPolling();
    }

    this.pollingSubscription = interval(pollingInterval).pipe(
      startWith(0),
      switchMap(() => this.postService.getPosts(0, 1)) // Only check the latest post
    ).subscribe(
      (posts : PostModel[]) => {
        if (posts.length > 0) {
          this.postCreatedSource.next(); // Notify of potential new posts
        }
      },
      (error : string) => {
        console.error('Error polling for posts:', error);
      }
    );
  }

  stopPolling() {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
      this.pollingSubscription = null;
    }
  }
}
