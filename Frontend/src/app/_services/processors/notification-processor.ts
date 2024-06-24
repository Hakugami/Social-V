import {Notification} from "../../_models/notification.model";

export interface NotificationProcessor {
    process(notification: Notification): Notification;
}
