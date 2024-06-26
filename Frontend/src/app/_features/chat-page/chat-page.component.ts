import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {DirectMessageMembersComponent} from "../direct-message-members/direct-message-members.component";
import {ChatWindowComponent} from "../chat-window/chat-window.component";
import {BehaviorSubject, Subscription} from "rxjs";
import {AsyncPipe} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

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
export class ChatPageComponent implements OnInit {
  private subscription = new Subscription();
  selectedUserId$ = new BehaviorSubject<string | null>(null);

  constructor(private cdr: ChangeDetectorRef, private route: ActivatedRoute) {
  }

  onUserSelected(userId: string) {
    console.log('User selected chat page component:', userId);
    this.selectedUserId$.next(userId);
  }

  ngOnInit(): void {
    this.subscription.add(
      this.route.params.subscribe(params => {
        this.onUserSelected(params['username']);
      })
    );
  }
}
