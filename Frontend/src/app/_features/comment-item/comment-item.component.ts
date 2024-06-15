import {Component, Input} from '@angular/core';
import {CommentModel} from '../../_models/comment.model';

@Component({
  selector: 'app-comment-item',
  standalone: true,
  imports: [],
  templateUrl: './comment-item.component.html',
  styleUrl: './comment-item.component.css'
})
export class CommentItemComponent {

  @Input() comment?: CommentModel;

  likeComment(comment: CommentModel) {
    // handle the "like" action
  }

  replyToComment(comment: CommentModel) {
    // handle the "reply" action
  }

  translateComment(comment: CommentModel) {
    // handle the "translate" action
  }
}
