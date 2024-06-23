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
    // this.stompClient.onConnect = () => {
    //   console.log('Connected to WebSocket server');
    // };
    // this.stompClient.activate();
  }

  subscribeToUserQueue(username: string) {
    if (this.stompClient) {
      console.log('after if condition');
      this.stompClient.onConnect = () => {
        console.log('subscribed to public queue');
        this.subscribeToPublicQueue();
        //this.subscribeToPrivateQueue(username);
        this.sendNotification('/app/public.notifications', {username});
      };
      this.stompClient.activate();
    }
  }

  subscribeToPublicQueue() {
    this.stompClient?.subscribe('/topic/public', (message: IMessage) => {
      console.log(message.body);
    });
  }

  subscribeToPrivateQueue(username: string) {
    this.stompClient?.subscribe(`/user/${username}/queue/messages`, (message: IMessage) => {
      console.log(message.body);
    });
  }

  sendNotification(destination: string, message: any) {
    this.stompClient?.publish({destination: destination, body: JSON.stringify(message)});
  }
}
