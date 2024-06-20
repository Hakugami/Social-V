import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import { NotificationDropdownComponent } from "../notification-dropdown/notification-dropdown.component";
import { FriendRequestDropdownComponent } from "../friend-request-dropdown/friend-request-dropdown.component";

@Component({
    selector: 'app-top-navbar',
    standalone: true,
    templateUrl: './top-navbar.component.html',
    styleUrl: './top-navbar.component.css',
    imports: [
        RouterLink,
        NotificationDropdownComponent,
        FriendRequestDropdownComponent
    ]
})
export class TopNavbarComponent {

}
