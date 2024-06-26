import { Injectable, EventEmitter, OnDestroy } from '@angular/core';
import { Client, IMessage, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from './auth.service';
import { MessageModel } from '../_models/message.model';
import { HttpClient } from '@angular/common/http';
import {GatewayEnvironment} from "../../environments/gateway.environment";

@Injectable({
  providedIn: 'root'
})
export class ChatService implements OnDestroy {
  private stompClient: Client | null = null;
  private chatApiUrl = `${GatewayEnvironment.chatApiUrl}/messages`;
  private messageSubject = new BehaviorSubject<MessageModel[]>([]);
  public messages$ = this.messageSubject.asObservable();
  public newMessageEvent = new EventEmitter<MessageModel>();

  constructor(private authService: AuthService, private http: HttpClient) {
    const username = this.authService.getUsername();
    if (username) {
      this.initializeWebSocketConnection(username);
    }
  }

  ngOnDestroy() {
    this.disconnectWebSocket();
  }

  private initializeWebSocketConnection(username: string) {
    this.stompClient = Stomp.over(() => new SockJS('http://localhost:9090/ws'));
    this.stompClient.onWebSocketClose = () => {
      console.log('WebSocket closed. Reconnect will be attempted in 1 second.');
      setTimeout(() => {
        this.initializeWebSocketConnection(username);
      }, 1000);
    };
    this.stompClient.onConnect = () => {
      this.subscribeToUserQueue(username);
    };
    this.stompClient.activate();
  }

  private disconnectWebSocket() {
    this.stompClient?.deactivate();
  }

  private subscribeToUserQueue(username: string) {
    if (this.stompClient) {
      this.subscribeToPublicQueue();
      this.subscribeToPrivateQueue(username);
    }
  }

  private subscribeToPublicQueue() {
    this.stompClient?.subscribe('/topic/public', (message: IMessage) => {
      console.log('Received from public queue: ' + message.body);
    });
  }

  private subscribeToPrivateQueue(username: string) {
    this.stompClient?.subscribe(`/user/${username}/queue/messages`, (message: IMessage) => {
      const chatMessage: MessageModel = JSON.parse(message.body);
      this.addMessage(chatMessage);
      this.newMessageEvent.emit(chatMessage);
    });
  }

  sendMessage(chatMessage: MessageModel): Promise<void> {
    return new Promise((resolve, reject) => {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.publish({
          destination: '/app/chat',
          body: JSON.stringify(chatMessage)
        });
        resolve();
      } else {
        reject(new Error('WebSocket not connected'));
      }
    });
  }

  fetchMessages(senderId: string, recipientId: string) {
    this.http.get<MessageModel[]>(`${this.chatApiUrl}/${senderId}/${recipientId}`)
      .subscribe(messages => this.messageSubject.next(messages));
  }

  private addMessage(message: MessageModel) {
    const currentMessages = this.messageSubject.getValue();
    this.messageSubject.next([...currentMessages, message]);
  }
}
