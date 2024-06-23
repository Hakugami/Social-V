import { Component, Input, input } from '@angular/core';
import { Notification } from '../../_models/notification.model';

@Component({
  selector: 'app-notification-item',
  standalone: true,
  imports: [],
  templateUrl: './notification-item.component.html',
  styleUrl: './notification-item.component.css'
})
export class NotificationItemComponent {
  @Input()
  notification!: Notification;

}
