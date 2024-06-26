import { Component, OnInit, OnDestroy, HostListener, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CreatePostComponent } from "../create-post/create-post.component";
import { TopNavbarComponent } from "../top-navbar/top-navbar.component";
import { UpcomingBirthdayTabComponent } from "../upcoming-birthday-tab/upcoming-birthday-tab.component";
import { PostItemComponent } from "../post-item/post-item.component";
import { PostModel } from '../../_models/post.model';
import { PostService } from '../../_services/post.service';
import { NgFor, NgIf } from '@angular/common';
import { PostUpdateService } from "../../_services/post-update.service";
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-news-feed',
  standalone: true,
  templateUrl: './news-feed.component.html',
  styleUrl: './news-feed.component.css',
  imports: [CreatePostComponent, TopNavbarComponent, UpcomingBirthdayTabComponent, PostItemComponent, NgFor, NgIf],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NewsFeedComponent implements OnInit, OnDestroy {
  posts: PostModel[] = [];
  currentPage = 0;
  pageSize = 10;
  isLoading = false;
  noMorePosts = false;
  private postUpdateSubscription: Subscription | undefined;

  constructor(
    private postService: PostService,
    private postUpdateService: PostUpdateService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadPosts();

    this.postUpdateSubscription = this.postUpdateService.postCreated$.subscribe(() => {
      this.resetAndReloadPosts();
    });

    this.postUpdateService.startPolling();
  }

  ngOnDestroy(): void {
    if (this.postUpdateSubscription) {
      this.postUpdateSubscription.unsubscribe();
    }
    this.postUpdateService.stopPolling();
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(): void {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100 && !this.isLoading && !this.noMorePosts) {
      this.loadPosts();
    }
  }

  private loadPosts(): void {
    if (this.isLoading || this.noMorePosts) return;

    this.isLoading = true;
    this.postService.getPosts(this.currentPage, this.pageSize).subscribe({
      next: (newPosts: PostModel[]) => {
        if (newPosts.length < this.pageSize) {
          this.noMorePosts = true;
        }
        this.updatePosts(newPosts);
        this.currentPage++;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Error fetching posts:', error);
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  private updatePosts(newPosts: PostModel[]): void {
    const updatedPosts = [...this.posts];
    newPosts.forEach(newPost => {
      const index = updatedPosts.findIndex(p => p.id === newPost.id);
      if (index !== -1) {
        if (JSON.stringify(updatedPosts[index]) !== JSON.stringify(newPost)) {
          updatedPosts[index] = newPost;
        }
      } else {
        updatedPosts.push(newPost);
      }
    });
    if (JSON.stringify(this.posts) !== JSON.stringify(updatedPosts)) {
      this.posts = updatedPosts;
    }
  }

  private resetAndReloadPosts(): void {
    this.posts = [];
    this.currentPage = 0;
    this.noMorePosts = false;
    this.loadPosts();
  }

  trackByPostId(index: number, post: PostModel): string {
    return post.id;
  }
}
