import {EventEmitter, Injectable} from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {LikeNotificationProcessor} from "./processors/like-notification-processor";
import {NotificationProcessor} from "./processors/notification-processor";
import {Notification} from "../_models/notification.model";
import {CommentNotificationProcessor} from "./processors/comment-notification-processor";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private stompClient: Client | null = null;
  public newNotificationEvent = new EventEmitter<Notification>();

  constructor() {
    this.stompClient = Stomp.over(() => new SockJS('http://localhost:8089/ws'));
  }
  private notificationProcessors: Map<string, NotificationProcessor> = new Map([
    ['LIKE', new LikeNotificationProcessor()],
   ['COMMENT', new CommentNotificationProcessor()],
    // Add more processors as needed
  ]);
  processNotification(notification: Notification): Notification {
    const processor = this.notificationProcessors.get(notification.notificationType);
    if (processor) {
      return processor.process(notification);
    }
    return notification;
  }

  subscribeToUserQueue(username: string) {
    if (this.stompClient) {

      this.stompClient.onConnect = () => {
        this.subscribeToPublicQueue();
        this.subscribeToPrivateQueue(username);
        this.sendNotification('/app/public.notifications', {username});
      };
      this.stompClient.activate();
    }
  }

  subscribeToPublicQueue() {
    this.stompClient?.subscribe('/topic/public', (message: IMessage) => {
      console.log('received from public queue' + message.body);
    });
  }

  subscribeToPrivateQueue(username: string) {
    console.log(username);
    this.stompClient?.subscribe(`/user/${username}/queue/notifications`, (message: IMessage) => {
      console.log('received from private queue' + message.body);
      let notification: Notification = JSON.parse(message.body);
      notification = this.processNotification(notification);
      this.newNotificationEvent.emit(notification);
    });
  }

  sendNotification(destination: string, message: any) {
    this.stompClient?.publish({destination: destination, body: JSON.stringify(message)});
  }
}
