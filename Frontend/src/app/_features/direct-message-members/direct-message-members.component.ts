import {Component, EventEmitter, Output} from '@angular/core';
import {NgClass, NgForOf} from "@angular/common";

@Component({
  selector: 'app-direct-message-members',
  standalone: true,
  imports: [
    NgForOf,
    NgClass
  ],
  templateUrl: './direct-message-members.component.html',
  styleUrl: './direct-message-members.component.css'
})
export class DirectMessageMembersComponent {
  currentUser: any = {
    id: 1,
    name: 'Bni Jordan',
    role: 'Web Designer',
    avatar: 'assets/images/user/1.jpg',
    status: 'online'
  };

  directMessages: any[] = [
    {
      id: 2,
      name: 'Paul Molive',
      role: 'translation by',
      avatar: 'assets/images/user/10.jpg',
      status: 'offline'
    },
    // Add more users as needed
  ];
  @Output() userSelected = new EventEmitter<number>();


  getStatusClass(status: string): string {
    switch (status) {
      case 'online':
        return 'text-success';
      case 'offline':
        return 'text-dark';
      case 'away':
        return 'text-warning';
      default:
        return 'text-dark';
    }
  }

  selectUser(userId: number) {
    this.userSelected.emit(userId);
  }
}
