import {Component} from '@angular/core';
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {PostItemComponent} from "../post-item/post-item.component";
import {NgForOf} from "@angular/common";
import {PostModel} from "../../_models/post.model";

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
export class ProfileTimelineComponent {
  posts: PostModel[] = [];

  constructor() {
    this.posts = [
      {
        id: 1,
        userImg: '../assets/images/user/1.jpg',
        userName: 'Bni Cyst',
        postTime: '3 hours ago',
        postImg: '../assets/images/page-img/p1.jpg',
        likes: 140,
        comments: [
          {
            id: 1,
            postId: 1,
            userImg: '../assets/images/user/02.jpg',
            name: 'Monty Carlo',
            email: 'monty@example.com',
            text: 'Lorem ipsum dolor sit amet',
            timestamp: '5 min'
          },
          {
            id: 2,
            postId: 1,
            userImg: '../assets/images/user/03.jpg',
            name: 'Paul Molive',
            email: 'paul@example.com',
            text: 'Lorem ipsum dolor sit amet',
            timestamp: '5 min'
          }
        ],
        shares: 99
      },
      {
        id: 2,
        userImg: '../assets/images/user/02.jpg',
        userName: 'Monty Carlo',
        postTime: '4 hours ago',
        postImg: '../assets/images/page-img/p2.jpg',
        likes: 140,
        comments: [
          {
            id: 1,
            postId: 2,
            userImg: '../assets/images/user/03.jpg',
            name: 'Paul Molive',
            email: 'test.com',
            text: 'Lorem ipsum dolor sit amet',
            timestamp: '5 min'
          },
          {
            id: 2,
            postId: 2,
            userImg: '../assets/images/user/04.jpg',
            name: 'Anna Mull',
            email: 'test.com',
            text: 'Lorem ipsum dolor sit amet',
            timestamp: '5 min'
          },
          {
            id: 3,
            postId: 2,
            userImg: '../assets/images/user/05.jpg',
            name: 'Gail Forcewind',
            email: 'test.com',
            text: 'Lorem ipsum dolor sit amet',
            timestamp: '5 min'
          }
        ],
        shares: 0
      }
    ];

  }

}
