import {Component} from '@angular/core';
import {CommentItemComponent} from "../comment-item/comment-item.component";
import {CommentModel} from "../../_models/comment.model";
import {CommonModule} from "@angular/common";

@Component({
  selector: 'app-post-item',
  standalone: true,
  imports: [
    CommentItemComponent,
    CommonModule
  ],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})
export class PostItemComponent {
  comments: CommentModel[] = [];


  constructor() {
    this.comments = [
      {
        userImg: 'assets/images/user/02.jpg',
        name: 'Monty Carlo',
        text: 'Lorem ipsum dolor sit amet',
        timestamp: '5 min',
        id: 0,
        postId: 0,
        email: ''
      },
      {
        userImg: 'assets/images/user/03.jpg',
        name: 'Paul Molive',
        text: 'Lorem ipsum dolor sit amet',
        timestamp: '5 min',
        id: 0,
        postId: 0,
        email: ''
      },
      {
        userImg: 'assets/images/user/04.jpg',
        name: 'Anna Mull',
        text: 'Lorem ipsum dolor sit amet',
        timestamp: '5 min',
        id: 0,
        postId: 0,
        email: ''
      }
    ];
  }
}
