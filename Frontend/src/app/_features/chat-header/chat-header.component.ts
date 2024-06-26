// chat-header.component.ts
import { Component, Input } from '@angular/core';
import {NgClass, NgIf} from "@angular/common";
import {UserModelDTO} from "../../_models/usermodel.model";


@Component({
  selector: 'app-chat-header',
  templateUrl: './chat-header.component.html',
  standalone: true,
  imports: [
    NgClass,
    NgIf
  ],
  styleUrls: ['./chat-header.component.css']
})
export class ChatHeaderComponent {
  @Input() selectedUser: UserModelDTO | null = null;

  getStatusClass(status: string): string {
    switch (status) {
      case 'online':
        return 'text-success';
      case 'offline':
        return 'text-danger';
      case 'away':
        return 'text-warning';
      default:
        return 'text-secondary';
    }
  }
}
