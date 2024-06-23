export interface CommentRequestModel {
  postId: string;
  userId: string | null;
  username: string;
  content: string;
}
