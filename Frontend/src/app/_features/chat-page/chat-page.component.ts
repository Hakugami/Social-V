import { Component, ChangeDetectorRef } from '@angular/core';
import { DirectMessageMembersComponent } from "../direct-message-members/direct-message-members.component";
import { ChatWindowComponent } from "../chat-window/chat-window.component";
import {BehaviorSubject} from "rxjs";
import {AsyncPipe} from "@angular/common";

@Component({
  selector: 'app-chat-page',
  standalone: true,
  imports: [
    DirectMessageMembersComponent,
    ChatWindowComponent,
    AsyncPipe
  ],
  templateUrl: './chat-page.component.html',
  styleUrl: './chat-page.component.css'
})
export class ChatPageComponent {
  selectedUserId$ = new BehaviorSubject<string | null>(null);

  constructor(private cdr: ChangeDetectorRef) {}

  onUserSelected(userId: string) {
    console.log('User selected chat page component:', userId);
    this.selectedUserId$.next(userId);
  }
}
