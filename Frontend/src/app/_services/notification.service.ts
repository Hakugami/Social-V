import { Injectable } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private stompClient: Client | null = null;

  constructor() {

  }
  subscribeToUserQueue(username: string) {
    this.stompClient = Stomp.over(new SockJS('http://localhost:8089/ws'));

    this.stompClient.onConnect = (frame) => {
      console.log('Connected: ' + frame);
      this.stompClient?.subscribe('/user/notifications', (message: IMessage) => {
        console.log(message.body);
      });

      this.stompClient!.subscribe(`/user/${username}/queue/messages`, (message: IMessage) => {
        console.log(message.body);
      });

    };

    this.stompClient.activate();

  }

  // sendNotification(notification: any) {
  //   this.stompClient.publish({destination: '/app/friendRequest', body: JSON.stringify(notification)});
  // }
}
