import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PostItemComponent} from "./_features/post-item/post-item.component";
import {CommentItemComponent} from "./_features/comment-item/comment-item.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PostItemComponent, CommentItemComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'SocialNetworkV2';
}
