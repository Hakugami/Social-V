import {CommentModel} from "./comment.model";

export interface PostModel {
  id: number;
  userImg: string;
  userName: string;
  postTime: string;
  postImg: string;
  likes: number;
  comments: CommentModel[];
  shares: number;
}
