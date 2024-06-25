// chat-window.component.ts
import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {ChatMessageComponent} from "../chat-message/chat-message.component";
import {FormsModule} from "@angular/forms";
import {ChatHeaderComponent} from "../chat-header/chat-header.component";
import {MessageModel} from "../../_models/message.model";
import {AuthService} from "../../_services/auth.service";
import {UserModelDTO} from "../../_models/usermodel.model";
import {FriendRequestsService} from "../../_services/friend-request.service";


interface User {
  id: number;
  name: string;
  avatar: string;
  status: 'online' | 'offline' | 'away';
}


@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  standalone: true,
  imports: [
    NgIf,
    ChatMessageComponent,
    FormsModule,
    ChatHeaderComponent,
    NgClass,
    NgForOf,
    DatePipe
  ],
  styleUrls: ['./chat-window.component.css']
})
export class ChatWindowComponent implements OnChanges {
  @Input() selectedUserId: string | null = null;
  selectedUser: UserModelDTO | null = null;
  messages: MessageModel[] = [];
  newMessage: string = '';

  constructor(private authService: AuthService, private friendRequestsService: FriendRequestsService) {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedUserId']) {
      this.loadUser();
      this.loadMessages();
    }
  }

  loadUser() {
    this.friendRequestsService.friends$.subscribe(friends => {
      this.selectedUser = friends.find(friend => friend.username === this.selectedUserId) || null;
    });

  }

  loadMessages() {
    // Here you would typically load messages from a service based on selectedUserId
    this.messages = [
      {
        senderId: this.authService.getUsername(), content: 'Hello!',
        recipientId: ''
      }
    ];
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      const newMsg: MessageModel = {
        senderId: this.authService.getUsername(),
        recipientId: this.selectedUserId ? this.selectedUserId.toString() : '',
        content: this.newMessage,
      };
      this.messages.push(newMsg);
      this.newMessage = '';
    }
  }

  checkMine(message: MessageModel): boolean {
    return message.senderId === this.authService.getUsername();
  }
}
