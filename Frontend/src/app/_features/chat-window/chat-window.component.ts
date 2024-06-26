import {Component, Input, OnChanges, SimpleChanges, OnInit, OnDestroy, ChangeDetectionStrategy, ChangeDetectorRef} from '@angular/core';
import {DatePipe, NgClass, NgForOf, NgIf} from "@angular/common";
import {ChatMessageComponent} from "../chat-message/chat-message.component";
import {FormsModule} from "@angular/forms";
import {ChatHeaderComponent} from "../chat-header/chat-header.component";
import {MessageModel} from "../../_models/message.model";
import {AuthService} from "../../_services/auth.service";
import {UserModelDTO} from "../../_models/usermodel.model";
import {SharedFriendRequestService} from "../../_services/shared-friend-request.service";
import {Subscription} from 'rxjs';

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
  styleUrls: ['./chat-window.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatWindowComponent implements OnChanges, OnInit, OnDestroy {
  private _selectedUserId: string | null = null;
  selectedUser: UserModelDTO | null = null;
  messages: MessageModel[] = [];
  newMessage: string = '';
  private friendsSubscription: Subscription | undefined;


  @Input()
  set selectedUserId(value: string | null) {
    console.log('selectedUserId changed:', value);
    this._selectedUserId = value;
    // Trigger user and message loading when the ID changes
    this.loadUser();
    this.loadMessages();
  }

  get selectedUserId(): string | null {
    return this._selectedUserId;
  }

  constructor(
    private authService: AuthService,
    private sharedFriendRequestService: SharedFriendRequestService,
    private cdr: ChangeDetectorRef
  ) {
    console.log('Chat window component initialized constructor ' + this.selectedUserId);
  }

  ngOnInit() {
    this.subscribeTofriends();
    console.log('Chat window component initialized' + this.selectedUserId);
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['selectedUserId']) {
      this.loadUser();
      this.loadMessages();
    }
  }

  ngOnDestroy() {
    if (this.friendsSubscription) {
      this.friendsSubscription.unsubscribe();
    }
  }

  private subscribeTofriends() {
    this.friendsSubscription = this.sharedFriendRequestService.friends$.subscribe(friends => {
      this.loadUser(friends);
    });
  }

  loadUser(friends: UserModelDTO[] = []) {
    const newSelectedUser = friends.find(friend => friend.username === this.selectedUserId) || null;
    if (JSON.stringify(this.selectedUser) !== JSON.stringify(newSelectedUser)) {
      this.selectedUser = newSelectedUser;
      this.cdr.detectChanges();
    }
  }

  loadMessages() {
    // Here you would typically load messages from a service based on selectedUserId
    const newMessages = [
      {
        senderId: this.authService.getUsername(),
        content: 'Hello!',
        recipientId: ''
      }
    ];
    if (JSON.stringify(this.messages) !== JSON.stringify(newMessages)) {
      this.messages = newMessages;
      this.cdr.detectChanges();
    }
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      const newMsg: MessageModel = {
        senderId: this.authService.getUsername(),
        recipientId: this.selectedUserId ? this.selectedUserId.toString() : '',
        content: this.newMessage,
      };
      this.messages = [...this.messages, newMsg];
      this.newMessage = '';
      this.cdr.detectChanges();
    }
  }

  checkMine(message: MessageModel): boolean {
    return message.senderId === this.authService.getUsername();
  }

  trackByFn(index: number, message: MessageModel): string {
    return `${message.senderId}-${message.recipientId}-${index}`;
  }
}
