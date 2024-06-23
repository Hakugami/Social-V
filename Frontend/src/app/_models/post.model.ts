import {CommentModel} from "./comment.model";
import {LikesModel} from "./likes.model";

export interface PostModel {
  id:        string;
  userId:    string;
  username:  string;
  content:   string;
  createdAt: Date;
  likes:     LikesModel;
  comments:  CommentModel[];
  videoUrl:  string;
  imagesUrl: string[];
  profilePicture: string;
}







