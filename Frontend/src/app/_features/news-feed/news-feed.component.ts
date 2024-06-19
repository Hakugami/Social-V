import { Component, OnInit } from '@angular/core';
import { CreatePostComponent } from "../create-post/create-post.component";
import { TopNavbarComponent } from "../top-navbar/top-navbar.component";
import { UpcomingBirthdayTabComponent } from "../upcoming-birthday-tab/upcoming-birthday-tab.component";
import { PostItemComponent } from "../post-item/post-item.component";
import { HttpClient } from '@angular/common/http';
import { PostModel } from '../../_models/post.model';
import { PostService } from '../../_services/post.service';
import { NgFor } from '@angular/common';

@Component({
    selector: 'app-news-feed',
    standalone: true,
    templateUrl: './news-feed.component.html',
    styleUrl: './news-feed.component.css',
    imports: [CreatePostComponent, TopNavbarComponent, UpcomingBirthdayTabComponent, PostItemComponent,NgFor]
})
export class NewsFeedComponent implements OnInit{

  posts: PostModel[] = [];

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
  }




}
