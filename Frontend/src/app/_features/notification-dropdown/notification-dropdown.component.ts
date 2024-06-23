import { Component } from '@angular/core';
import { NotificationItemComponent } from "../notification-item/notification-item.component";
import { Notification } from '../../_models/notification.model';
import { NgFor } from '@angular/common';

@Component({
    selector: 'app-notification-dropdown',
    standalone: true,
    templateUrl: './notification-dropdown.component.html',
    styleUrl: './notification-dropdown.component.css',
    imports: [NotificationItemComponent,NgFor]
})
export class NotificationDropdownComponent {
    notifications: Notification[] = [
        {
            id: 1,
            title: 'New Friend Request',
            description: 'John Doe has sent you a friend request.',
            image: "assets/images/icon/01.png",
            time: 'now'
        },
    ];
number: number = this.notifications.length;

}
