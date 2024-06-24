import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {CreatePostComponent} from "../create-post/create-post.component";
import {FriendMiniCardItemComponent} from "../friend-mini-card-item/friend-mini-card-item.component";
import {PhotoMiniCardItemComponent} from "../photo-mini-card-item/photo-mini-card-item.component";
import {PostItemComponent} from "../post-item/post-item.component";
import {NgForOf} from "@angular/common";
import {PostModel} from "../../_models/post.model";
import { PostService } from '../../_services/post.service';
import {UserModelDTO} from "../../_models/usermodel.model";
import {PublicUserModel} from "../../shared/PublicUserModel";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../_services/auth.service";

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

  constructor(private postService: PostService,private http: HttpClient) {}

  ngOnInit(): void {
    /**
     * While loading time line , as it is the home page main component , load first user data to be displayed all over the application
     * like use email , user name , image , etc ....
     * *** Note that i made it before loading posts as loading posts is a heavy process .
     */
    this.http.get<UserModelDTO>(`http://localhost:8081/profile/edit/${PublicUserModel.email}`).subscribe({
      next: (data) => {
        PublicUserModel.user_model = data;
      },
      error: (error) => {
        console.error('Error fetching user data:', error);
      }
    });
    this.postService.getPosts().subscribe(data => {
      this.posts = data;
    });
  }
}
