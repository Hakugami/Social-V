// import { Injectable } from '@angular/core';
// import { Notification } from './notification.model'; // Assuming you have a Notification model
//
// @Injectable({
//   providedIn: 'root'
// })
// export class NotificationHandlerService {
//
//   handleNotification(notification: Notification) {
//     const handlers = {
//       'like': this.handleLikeNotification,
//       'comment': this.handleCommentNotification,
//       'friend request': this.handleFriendRequestNotification
//     };
//
//     const handler = handlers[notification.notificationType];
//     if (handler) {
//       handler(notification);
//     } else {
//       console.log('Received an unknown notification type');
//     }
//   }
//
//   private handleLikeNotification(notification: Notification) {
//     // Handle like notification
//     console.log('Received a like notification');
//   }
//
//   private handleCommentNotification(notification: Notification) {
//     // Handle comment notification
//     console.log('Received a comment notification');
//   }
//
//   private handleFriendRequestNotification(notification: Notification) {
//     // Handle friend request notification
//     console.log('Received a friend request notification');
//   }
// }
