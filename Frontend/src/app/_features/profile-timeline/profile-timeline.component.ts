import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
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
  @Input() userId!: string;

  constructor(private postService: PostService,private http: HttpClient,private authService:AuthService) {}

  ngOnInit(): void {
    this.postService.getPostById(this.userId).subscribe(data => {
      this.posts = data;
    });
  }
}
