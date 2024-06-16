import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {PostItemComponent} from "./_features/post-item/post-item.component";
import {CommentItemComponent} from "./_features/comment-item/comment-item.component";
import {ProfilePageComponent} from "./_features/profile-page/profile-page.component";
import {TopNavbarComponent} from "./_features/top-navbar/top-navbar.component";
import {RightNavbarComponent} from "./_features/right-navbar/right-navbar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, PostItemComponent, CommentItemComponent, ProfilePageComponent, TopNavbarComponent, RightNavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'SocialNetworkV2';
}
