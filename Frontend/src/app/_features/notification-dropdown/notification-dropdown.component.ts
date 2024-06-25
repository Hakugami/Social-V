import {Component, OnInit} from '@angular/core';
import { NotificationItemComponent } from "../notification-item/notification-item.component";
import { Notification } from '../../_models/notification.model';
import { NgFor } from '@angular/common';
import {NotificationService} from "../../_services/notification.service";
import {AuthService} from "../../_services/auth.service";
import {NotificationDto} from "../../_models/notification-dto.model";

@Component({
    selector: 'app-notification-dropdown',
    standalone: true,
    templateUrl: './notification-dropdown.component.html',
    styleUrl: './notification-dropdown.component.css',
    imports: [NotificationItemComponent,NgFor]
})
export class NotificationDropdownComponent implements OnInit {
  notifications: Notification[] = [];
  number: number = 0;

  constructor(private notificationService: NotificationService,
              private authService: AuthService) {
    this.notificationService.newNotificationEvent.subscribe((notification: Notification) => {
      this.notifications.push(notification);
      this.number = this.notifications.length;
    });

  }

  ngOnInit() {
    console.log('loading notifications');
    this.notificationService.loadNotificationsForCurrentUser().subscribe((notifications: Notification[]) => {
        this.notifications = notifications;
        console.log(notifications);
        this.number = this.notifications.length;
      }
    );
  }




}
