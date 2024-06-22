import {Component, OnInit} from '@angular/core';
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {PostItemComponent} from "../post-item/post-item.component";
import {NgForOf} from "@angular/common";
import {PostModel} from "../../_models/post.model";
import { PostService } from '../../_services/post.service';

@Component({
  selector: 'app-profile-timeline',
  standalone: true,
  imports: [
    CreatePostComponent,
    FriendMiniCardItemComponent,
    PhotoMiniCardItemComponent,
    PostItemComponent,
    NgForOf
  ],
  templateUrl: './profile-timeline.component.html',
  styleUrl: './profile-timeline.component.css'
})
export class ProfileTimelineComponent implements OnInit{
  posts: PostModel[] = [];

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
  }

}
