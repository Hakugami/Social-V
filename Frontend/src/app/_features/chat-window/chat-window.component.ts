// chat-window.component.ts
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import { ChatMessageComponent } from "../chat-message/chat-message.component";
import { FormsModule } from "@angular/forms";
import { ChatHeaderComponent } from "../chat-header/chat-header.component";
import {MessageModel} from "../../_models/message.model";

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
  @Input() selectedUserId: number | null = null;
  selectedUser: User | null = null;
  messages: MessageModel[] = [];
  newMessage: string = '';

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedUserId']) {
      this.loadUser();
      this.loadMessages();
    }
  }

  loadUser() {
    // In a real application, you would fetch this data from a service
    if (this.selectedUserId) {
      this.selectedUser = {
        id: this.selectedUserId,
        name: 'Paul Molive',
        avatar: 'assets/images/user/10.jpg',
        status: 'online'
      };
    } else {
      this.selectedUser = null;
    }
  }

  loadMessages() {
    // Here you would typically load messages from a service based on selectedUserId
    this.messages = [
      { id: 1, sender: 'User', content: 'Hello!',  isMine: false },
      { id: 2, sender: 'You', content: 'Hi there!', isMine: true },
    ];
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      const newMsg: MessageModel = {
        id: this.messages.length + 1,
        sender: 'You',
        content: this.newMessage,
        isMine: true
      };
      this.messages.push(newMsg);
      this.newMessage = '';
    }
  }
}
