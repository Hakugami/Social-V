// chat-header.component.ts
import { Component, Input } from '@angular/core';
import {NgClass, NgIf} from "@angular/common";

interface User {
  id: number;
  name: string;
  avatar: string;
  status: 'online' | 'offline' | 'away';
}

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
  @Input() selectedUser: User | null = null;

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
