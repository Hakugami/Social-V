import { Component } from '@angular/core';
import { NotificationItemComponent } from "../notification-item/notification-item.component";
import { Notification } from '../../_models/notification.model';
import { NgFor } from '@angular/common';
import {NotificationService} from "../../_services/notification.service";

@Component({
    selector: 'app-notification-dropdown',
    standalone: true,
    templateUrl: './notification-dropdown.component.html',
    styleUrl: './notification-dropdown.component.css',
    imports: [NotificationItemComponent,NgFor]
})
export class NotificationDropdownComponent {
  notifications: Notification[] = [];
  number: number = 0;

  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.notificationService.newNotificationEvent.subscribe((notification: Notification) => {
      this.notifications.push(notification);
      this.number = this.notifications.length;
    });
  }

}
