import {Notification} from "../../_models/notification.model";
import {NotificationProcessor} from "./notification-processor";

export class LikeNotificationProcessor implements NotificationProcessor{
  process(notification: Notification): Notification {
    notification.title = 'New Like';
    notification.description = `${notification.senderUsername} liked your post`;
    notification.image = 'assets/images/icon/01.png';
    return notification;
  }

}

