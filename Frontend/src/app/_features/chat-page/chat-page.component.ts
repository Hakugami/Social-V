import { Component } from '@angular/core';
import {DirectMessageMembersComponent} from "../direct-message-members/direct-message-members.component";
import {ChatWindowComponent} from "../chat-window/chat-window.component";

@Component({
  selector: 'app-chat-page',
  standalone: true,
  imports: [
    DirectMessageMembersComponent,
    ChatWindowComponent
  ],
  templateUrl: './chat-page.component.html',
  styleUrl: './chat-page.component.css'
})
export class ChatPageComponent {
  selectedUserId: number | null = null;

  onUserSelected(userId: number) {
    this.selectedUserId = userId;
  }
}