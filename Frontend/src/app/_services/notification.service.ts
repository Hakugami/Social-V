import { Injectable } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private stompClient: Client | null = null;

  constructor() {
    this.stompClient = Stomp.over(() => new SockJS('http://localhost:8089/ws'));
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
    });
  }

  sendNotification(destination: string, message: any) {
    this.stompClient?.publish({destination: destination, body: JSON.stringify(message)});
  }
}
