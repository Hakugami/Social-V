
import { NotificationProcessor } from './notification-processor';
import {Notification} from "../../_models/notification.model";

export class CommentNotificationProcessor implements NotificationProcessor {
    process(notification: Notification): Notification {
        notification.title = 'New Comment';
        notification.description = `${notification.senderUsername} commented on your post`;
        notification.image = 'assets/images/icon/comment.png';
        return notification;
    }
}
