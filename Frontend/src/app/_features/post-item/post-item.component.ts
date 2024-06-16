import {Component, Input, OnInit} from '@angular/core';
import {CommentItemComponent} from "../comment-item/comment-item.component";
import {CommentModel} from "../../_models/comment.model";
import {CommonModule} from "@angular/common";
import {PostModel} from "../../_models/post.model";

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
export class PostItemComponent implements OnInit {
  comments: CommentModel[] = [];
  @Input() post!: PostModel;

  constructor() { }

  ngOnInit() {
    if (this.post) {
      this.comments = this.post.comments;
    }
  }
}
