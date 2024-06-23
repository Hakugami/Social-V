import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {CommentItemComponent} from "../comment-item/comment-item.component";
import {CommentModel} from "../../_models/comment.model";
import {CommonModule} from "@angular/common";
import {PostModel} from "../../_models/post.model";
import {RouterLink} from "@angular/router";
import {PostService} from "../../_services/post.service";
import {AuthService} from "../../_services/auth.service";
import {FormsModule} from "@angular/forms";
import {Emotion} from "../../_models/_enums/Emotion";
import {LikeRequestModel} from "../../_models/like-request.model";
import {LikesModel} from "../../_models/likes.model";

@Component({
  selector: 'app-post-item',
  standalone: true,
  imports: [
    CommentItemComponent,
    CommonModule,
    RouterLink,
    FormsModule
  ],
  templateUrl: './post-item.component.html',
  styleUrl: './post-item.component.css'
})
export class PostItemComponent implements OnInit {
  comments: CommentModel[] = [];
  @Input() post!: PostModel;
  commentText: string = '';
  isLiked = false;
  selectedEmotion: Emotion = Emotion.LIKE;
  Emotion = Emotion; // expose the Emotion enum to the template
  selectedIconSrc: string = '../../../assets/images/icon/01.png'; // default icon
  EmotionIconMap = {
    [Emotion.LIKE]: '../../../assets/images/icon/01.png',
    [Emotion.LOVE]: '../../../assets/images/icon/02.png',
    [Emotion.HAPPY]: '../../../assets/images/icon/03.png',
    [Emotion.HAHA]: '../../../assets/images/icon/04.png',
    [Emotion.THINK]: '../../../assets/images/icon/05.png',
    [Emotion.SAD]: '../../../assets/images/icon/06.png',
    [Emotion.LOVELY]: '../../../assets/images/icon/07.png'
  }



  selectEmotion(emotion: Emotion, iconSrc: string) {
    if(this.isLiked && !(this.selectedEmotion === emotion)){
      this.selectedEmotion = emotion;
      this.selectedIconSrc = iconSrc;
      this.updateLike();
    }else {
      this.selectedEmotion = emotion;
      this.selectedIconSrc = iconSrc;
      this.toggleLike();
    }
  }

  toggleLike() {
    this.isLiked = !this.isLiked;
    if (this.isLiked) {
      // Send a request to the server to like the post
      this.addLike();

    } else {
      // Send a request to the server to remove the like
      this.removeLike();

    }
  }

  removeLike() {
    const likeId = this.post.likes.likes.find(like => like.username === this.authService.getUsername())?.likeId;
    console.log('Like ID:', likeId);
    if (likeId) {
      this.postService.removeLike(likeId.toString()).subscribe({
        next: (response: any) => {
          console.log('Received response:', response);
          // Update the likes list in the post locally to reflect the change
          this.post.likes.numberOfLikes -= 1;
          this.post.likes.likes = this.post.likes.likes.filter(like => like.likeId !== likeId);
          this.selectedIconSrc = this.EmotionIconMap[Emotion.LIKE];
          this.selectedEmotion = Emotion.LIKE;
          console.log('Updated post:', this.post);

        },
        error: (error) => {
          console.error('Error fetching posts:', error);
        }
      });
    }
  }

  updateLike() {
    const likeId = this.post.likes.likes.find(like => like.username === this.authService.getUsername())?.likeId;
    console.log('Like ID:', likeId);
    if (likeId) {
      this.postService.updateLike(likeId.toString(), this.selectedEmotion).subscribe({
        next: (response: any) => {
          console.log('Received response:', response);
          // Update the likes list in the post locally to reflect the change
          this.post.likes.likes = this.post.likes.likes.map(like => {
            if (like.likeId === likeId) {
              like.emotion = this.selectedEmotion;
            }
            return like;
          });
          console.log('Updated post:', this.post);

        },
        error: (error) => {
          console.error('Error fetching posts:', error);
        }
      });
    }
  }

  addLike() {
    // Send a request to the server to like the post
    const postId = this.post.id;
    const likeRequest: LikeRequestModel = {
      postId: postId,
      userId: null,
      username: this.authService.getUsername(),
      emotion: this.selectedEmotion
    }
    this.postService.likePost(likeRequest).subscribe({
      next: (response: any) => {
        console.log('Received response:', response);
        this.post.likes = response;
        const likeModel : LikesModel = response;
        this.selectedEmotion = response.likes.find((like: { username: string; }) => like.username === this.authService.getUsername())?.emotion!;

      },
      error: (error) => {
        console.error('Error fetching posts:', error);
      }
    });
  }

  submitComment() {
    const commentRequest = {
      postId: this.post.id,
      userId: null, // replace with actual user id
      username: this.authService.getUsername(),
      content: this.commentText
    }
    this.postService.addComment(commentRequest).subscribe({
      next: (response: any) => {
        console.log('Received response:', response);
        // Add the new comment to the post's comments list
        const commentModel : CommentModel = response;
        commentModel.profilePicture = this.post.profilePicture;
        this.post.comments.push(response);
        // Clear the comment text field
        this.commentText = '';
      },
      error: (error) => {
        console.error('Error adding comment:', error);
      }
    });
  }

  constructor(private postService: PostService, private authService: AuthService) {
  }

  ngOnInit() {
    // Check if the current user's username is in the likes list
    const username = this.authService.getUsername();
    this.isLiked = this.post.likes.likes.some(like => like.username === username);
    if (this.isLiked) {
      // If the user has liked the post, set the selected emotion to the one they used
      this.selectedEmotion = this.post.likes.likes.find((like: { username: string; }) => like.username === this.authService.getUsername())?.emotion!;
      this.selectedIconSrc = this.EmotionIconMap[this.selectedEmotion];
      console.log('Selected emotion:', this.selectedEmotion);
    }
  }

  getDisplayedLikes(limit: number = 5): { username: string; likeId: string }[] {
    return this.post.likes.likes.map(like => ({...like, likeId: like.likeId.toString()})).slice(0, limit);
  }

  closeDropdown() {
    // Close the dropdown menu when the user clicks outside of it
    document.getElementById('dropdown')?.classList.remove('show');

  }



}
