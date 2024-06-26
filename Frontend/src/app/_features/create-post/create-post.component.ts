import {Component, OnInit} from '@angular/core';
import {PostService} from '../../_services/post.service';
import {AuthService} from '../../_services/auth.service';
import {NgForOf, NgIf, NgOptimizedImage} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import {finalize, Observable} from "rxjs";
import { PublicUserModel } from '../../shared/PublicUserModel';
import { DefaultImageDirective } from '../../_directives/default-image.directive';
import {PostUpdateService} from "../../_services/post-update.service";

@Component({
  selector: 'app-create-post',
  standalone: true,
  templateUrl: './create-post.component.html',
  imports: [
    NgOptimizedImage,
    FormsModule,
    NgIf,
    NgForOf,
    DefaultImageDirective
  ],
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit{
  newPostContent: string = '';
  selectedFiles: File[] = [];
  selectedVideo: File | null = null;
  uploadProgress: number = 0;
  isUploading: boolean = false;
  userImage?: string | null | undefined;

  ngOnInit() {
    this.publicUserModel.userModel$.subscribe((userModel) => {
      this.userImage = userModel?.profilePicture;
    });
  }

  constructor(private postService: PostService, private authService: AuthService
              ,private publicUserModel: PublicUserModel
              ,private postUpdateService : PostUpdateService){
  }

  onFileSelected(event: any) {
    const files: FileList = event.target.files;

    for (let i = 0; i < files.length; i++) {
      const file = files[i];
      const fileExtension = file.name.split('.').pop()?.toLowerCase();

      if (['jpg', 'jpeg', 'png', 'gif'].includes(<string>fileExtension)) {
        // Image file
        this.selectedFiles.push(file);
      } else if (['mp4', 'avi', 'mov', 'wmv', 'mkv'].includes(<string>fileExtension)) {
        // Video file
        if (this.selectedVideo) {
          console.log('Replacing existing video with new selection');
        }
        this.selectedVideo = file;
      } else {
        console.error('Unsupported file type');
      }
    }
  }

  createPost() {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo) {
      const postDto = {
        content: this.newPostContent,
        username: userInfo.username,
        email: userInfo.email
      };
      console.log("selected files size" + this.selectedFiles.length)
      if (this.selectedFiles.length > 0) {
        // Image post
        const formData = this.createFormData(postDto, this.selectedFiles);
        this.createPostOfType('image', formData);
      }

      if (this.selectedVideo) {
        // Video post
        const formData = this.createFormData(postDto, [this.selectedVideo]);
        this.createPostOfType('video', formData);
      }

      if (!this.selectedFiles.length && !this.selectedVideo) {
        // Text post
        this.createTextPost(postDto);
      }
    }
  }

  createFormData(postDto: any, files: File[]): FormData {
    const formData = new FormData();
    formData.append('dto', JSON.stringify(postDto));
    for (let file of files) {
      console.log(file);
      formData.append('file', file, file.name); // use 'files' instead of 'file'
    }
    return formData;
  }

  createPostOfType(type: 'image' | 'video', formData: FormData) {
    this.isUploading = true;
    this.uploadProgress = 0;

    let observable: Observable<HttpEvent<any>>;
    if (type === 'image') {
      observable = this.postService.createImagePost(formData);
    } else {
      observable = this.postService.createVideoPost(formData);
    }

    observable.subscribe(
      (event: HttpEvent<any>) => {
        switch (event.type) {
          case HttpEventType.UploadProgress:
            const total = event.total || 1;
            this.uploadProgress = Math.round(100 * event.loaded / total);
            break;
          case HttpEventType.Response:
            console.log(`${type.charAt(0).toUpperCase() + type.slice(1)} post created successfully`, event.body);
            this.resetForm();
            this.isUploading = false;
            break;
        }
      },
      (error: any) => {
        console.error(`Error creating ${type} post`, error);
        this.isUploading = false;
      }
    );
  }

  createTextPost(postDto: any) {
    this.postService.createTextPost(postDto).pipe(finalize(() => this.postUpdateService.notifyPostCreated()))
      .subscribe((response: any) => {
      console.log('Text post created successfully', response);
      this.resetForm();
    }, (error: any) => {
      console.error('Error creating text post', error);
    });
  }

  resetForm() {
    this.newPostContent = '';
    this.selectedFiles = [];
    this.selectedVideo = null;
  }

  removeFile(index: number) {
    this.selectedFiles.splice(index, 1);
  }

  removeVideo() {
    this.selectedVideo = null;
  }
}
