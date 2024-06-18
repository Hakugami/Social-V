// services/post.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { PostModel } from '../_models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  // Simulated post data ,will change later to api call
  private posts: PostModel[] = [
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


  constructor() { }

  // Fetch posts (in a real application, this would be an HTTP call)
  getPosts(): Observable<PostModel[]> {
    return of(this.posts);
  }
}
