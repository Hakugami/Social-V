import {Component, Input} from '@angular/core';
import {CommentModel} from '../../_models/comment.model';
import {DefaultImageDirective} from "../../_directives/default-image.directive";

@Component({
  selector: 'app-comment-item',
  standalone: true,
  imports: [
    DefaultImageDirective
  ],
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
