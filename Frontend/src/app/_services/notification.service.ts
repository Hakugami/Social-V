import {EventEmitter, Injectable} from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import {LikeNotificationProcessor} from "./processors/like-notification-processor";
import {NotificationProcessor} from "./processors/notification-processor";
import {Notification} from "../_models/notification.model";
import {CommentNotificationProcessor} from "./processors/comment-notification-processor";
import {jwtDecode} from "jwt-decode";
import {map, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {NotificationDto} from "../_models/notification-dto.model";



@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private stompClient: Client | null = null;
  public newNotificationEvent = new EventEmitter<Notification>();
  private notificationApiUrl = environment.notificationApiUrl;

  constructor(private http: HttpClient) {
    const username = this.getUsername()
    if(username){
      this.initializeWebSocketConnection(username);
    }
  }
  getUsername(): string {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded: any = jwtDecode(token);
      return decoded.username;
    }
    return '';
  }
  ngOnDestroy() {
    this.disconnectWebSocket();
  }


  initializeWebSocketConnection(username: string) {
    this.stompClient = Stomp.over(() => new SockJS('http://localhost:8089/ws'));
    this.stompClient.onWebSocketClose = () => {
      console.log('WebSocket closed. Reconnect will be attempted in 1 second.');
      setTimeout(() => {
        this.initializeWebSocketConnection(username);
      }, 1000);
    };
    this.stompClient.onConnect = () => {
      console.log('connected enta fen yabny');
      this.subscribeToUserQueue(username);
    };
    this.stompClient.activate();
  }

  disconnectWebSocket() {
    this.stompClient?.deactivate();
  }

  private notificationProcessors: Map<string, NotificationProcessor> = new Map([
    ['LIKE', new LikeNotificationProcessor()],
    ['COMMENT', new CommentNotificationProcessor()]
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
      this.subscribeToPublicQueue();
      this.subscribeToPrivateQueue(username);
      this.sendNotification('/app/public.notifications', {username});
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
  findNotificationByReceiverUsername(username: string): Observable<NotificationDto[]> {
    return this.http.get<NotificationDto[]>(`${this.notificationApiUrl}/${username}`);
  }


  loadNotificationsForCurrentUser(): Observable<Notification[]> {
    const username = this.getUsername();
    return this.findNotificationByReceiverUsername(username).pipe(
      map((notificationDtos: NotificationDto[]) =>
        notificationDtos.map((notificationDto: NotificationDto) => this.mapToNotification(notificationDto))
      ),
      map((notifications: Notification[]) =>
        notifications.map((notification: Notification) => this.processNotification(notification))
      )
    );
  }

  private mapToNotification(notificationDto: NotificationDto): Notification {
    return {
      id: 0,
      senderUsername: notificationDto.senderUsername,
      receiverUsername: notificationDto.receiverUsername,
      notificationType: notificationDto.notificationType,
      message: notificationDto.message,
      title: '', // Add appropriate mapping
      description: '', // Add appropriate mapping
      image: '', // Add appropriate mapping
      time: '' // Add appropriate mapping
    };
  }
}
