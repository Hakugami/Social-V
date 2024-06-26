import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CreatePostComponent } from "../create-post/create-post.component";
import { FriendMiniCardItemComponent } from "../friend-mini-card-item/friend-mini-card-item.component";
import { PhotoMiniCardItemComponent } from "../photo-mini-card-item/photo-mini-card-item.component";
import { PostItemComponent } from "../post-item/post-item.component";
import {NgForOf, NgIf} from "@angular/common";
import { PostModel } from "../../_models/post.model";
import { PostService } from '../../_services/post.service';
import { AuthService } from "../../_services/auth.service";

@Component({
  selector: 'app-profile-timeline',
  standalone: true,
  imports: [
    CreatePostComponent,
    FriendMiniCardItemComponent,
    PhotoMiniCardItemComponent,
    PostItemComponent,
    NgForOf,
    NgIf
  ],
  templateUrl: './profile-timeline.component.html',
  styleUrl: './profile-timeline.component.css'
})
export class ProfileTimelineComponent implements OnInit, OnChanges {
  @Input() username!: string ;
  @Input() isOwnProfile: boolean = false;

  posts: PostModel[] = [];

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['username']) {
      this.loadPosts();
    }
  }

  loadPosts(): void {
    if (this.username) {
      this.postService.getPostById(this.username).subscribe(
        data => {
          this.posts = data;
        },
        error => console.error('Error fetching posts:', error)
      );
    }
  }
}
