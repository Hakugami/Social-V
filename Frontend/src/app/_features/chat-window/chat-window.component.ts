import { Component, Input, OnInit, OnDestroy, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { BehaviorSubject, Subscription, combineLatest } from 'rxjs';
import { map } from 'rxjs/operators';
import { MessageModel } from "../../_models/message.model";
import { AuthService } from "../../_services/auth.service";
import { UserModelDTO } from "../../_models/usermodel.model";
import { SharedFriendRequestService } from "../../_services/shared-friend-request.service";
import { ChatHeaderComponent } from "../chat-header/chat-header.component";
import { ChatMessageComponent } from "../chat-message/chat-message.component";
import { AsyncPipe, JsonPipe, NgForOf, NgIf } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { ChatService } from "../../_services/chat.service";

@Component({
  selector: 'app-chat-window',
  templateUrl: './chat-window.component.html',
  styleUrls: ['./chat-window.component.css'],
  standalone: true,
  imports: [
    ChatHeaderComponent,
    ChatMessageComponent,
    AsyncPipe,
    JsonPipe,
    FormsModule,
    NgIf,
    NgForOf
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatWindowComponent implements OnInit, OnDestroy {
  private selectedUserIdSubject = new BehaviorSubject<string | null>(null);
  @Input() set selectedUserId(value: string | null) {
    console.log('selectedUserId setter called with value:', value);
    this.selectedUserIdSubject.next(value);
  }
  get selectedUserId(): string | null {
    return this.selectedUserIdSubject.getValue();
  }

  selectedUser$ = new BehaviorSubject<UserModelDTO | null>(null);
  messages$ = this.chatService.messages$;
  newMessage: string = '';
  private subscription: Subscription = new Subscription();

  constructor(
    private authService: AuthService,
    private sharedFriendRequestService: SharedFriendRequestService,
    private chatService: ChatService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.subscription.add(
      combineLatest([
        this.selectedUserIdSubject,
        this.sharedFriendRequestService.friends$
      ]).pipe(
        map(([selectedUserId, friends]) => {
          console.log('Combining selectedUserId and friends:', selectedUserId, friends);
          return friends.find(friend => friend.username === selectedUserId) || null;
        })
      ).subscribe(selectedUser => {
        console.log('New selected user:', selectedUser);
        this.selectedUser$.next(selectedUser);
        this.loadMessages();
        this.cdr.detectChanges();
      })
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  loadMessages() {
    const selectedUserId = this.selectedUserId;
    if (selectedUserId) {
      this.chatService.fetchMessages(this.authService.getUsername(), selectedUserId);
    }
  }

  sendMessage() {
    if (this.newMessage.trim() && this.selectedUserId) {
      const newMsg: MessageModel = {
        senderId: this.authService.getUsername(),
        recipientId: this.selectedUserId,
        content: this.newMessage,
      };
      this.chatService.sendMessage(newMsg);
      this.newMessage = '';
    }
  }

  checkMine(message: MessageModel): boolean {
    return message.senderId === this.authService.getUsername();
  }

  trackByFn(index: number, message: MessageModel): string {
    return `${message.senderId}-${message.recipientId}-${index}`;
  }
}
