import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgClass, NgForOf} from "@angular/common";
import {FriendRequestsService} from "../../_services/friend-request.service";

import {PublicUserModel} from "../../shared/PublicUserModel";
import {UserModelDTO} from "../../_models/usermodel.model";
import {DefaultImageDirective} from "../../_directives/default-image.directive";

@Component({
    selector: 'app-direct-message-members',
    standalone: true,
    imports: [
        NgForOf,
        NgClass,
        DefaultImageDirective
    ],
    templateUrl: './direct-message-members.component.html',
    styleUrl: './direct-message-members.component.css'
})
export class DirectMessageMembersComponent implements OnInit {
    directMessages: UserModelDTO[] = [];


    constructor(private friendRequestsService: FriendRequestsService,
                private publicUserModel: PublicUserModel) {
    }

    ngOnInit(): void {
        this.getFriends();
    }

    getFriends() {
        this.friendRequestsService.friends$.subscribe(friends => {
            this.directMessages = friends;
        });
    }

    currentUser: UserModelDTO | null = this.publicUserModel.getUserModel();

    @Output() userSelected = new EventEmitter<string>();


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

    selectUser(username: string) {
        this.userSelected.emit(username);
    }
}
