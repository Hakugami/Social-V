// chat-message.component.ts
import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';
import {MessageModel} from "../../_models/message.model";
import {AuthService} from "../../_services/auth.service";

@Component({
  selector: 'app-chat-message',
  templateUrl: './chat-message.component.html',
  standalone: true,
  imports: [NgIf],
  styleUrls: ['./chat-message.component.css']
})
export class ChatMessageComponent {
  @Input() message!:MessageModel ;
  @Input() isMine!: boolean;
}
