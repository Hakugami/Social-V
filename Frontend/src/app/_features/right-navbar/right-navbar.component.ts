import { Component } from '@angular/core';
import { Friend } from '../../_models/friend.model';
import { NgFor } from '@angular/common';
import { FriendStatusComponent } from "../friend-status/friend-status.component";

@Component({
    selector: 'app-right-navbar',
    standalone: true,
    templateUrl: './right-navbar.component.html',
    styleUrl: './right-navbar.component.css',
    imports: [NgFor, FriendStatusComponent]
})
export class RightNavbarComponent {
  friends:Friend[]= [
    {name:'John Doe',status:'status-online',image:'assets/images/user/01.jpg'},
    {name:'Richard Miles',status:'status-online',image:'assets/images/user/02.jpg'},
    {name:'John Smith',status:'status-offline',image:'assets/images/user/03.jpg'},

  ];

}
