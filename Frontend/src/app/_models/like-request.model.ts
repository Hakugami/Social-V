import {Emotion} from "./_enums/Emotion";

export interface LikeRequestModel {
    postId: string;
    userId: string | null;
    username: string;
    emotion: Emotion;
    postOwnerUsername: string;
}
