import { Component, OnInit } from '@angular/core';
import { RecommendationService } from '../../_services/recommendation.service';

import { FriendRequestsService } from '../../_services/friend-request.service';
import { AuthService } from '../../_services/auth.service';
import { NgFor, NgIf } from '@angular/common';
import { PersonCardComponent } from '../person-card/person-card.component';
import {UserModelDTO} from "../../_models/usermodel.model";

@Component({
  selector: 'app-people-you-may-know',
  templateUrl: './people-you-might-know.component.html',
  styleUrls: ['./people-you-might-know.component.css'],
  standalone: true,
  imports: [NgFor,PersonCardComponent,NgIf]
})
export class PeopleYouMayKnowComponent implements OnInit {
  recommendations: UserModelDTO[] = [];

  constructor(
    private recommendationService: RecommendationService,
    private friendRequestService: FriendRequestsService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.getRecommendations();
  }

  getRecommendations(): void {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo) {
      const userId = userInfo.email;
      const limit = 10;
      this.recommendationService.getRecommendations(userId, limit).subscribe(
        (data) => {
          this.recommendations = data;
          this.filterOutExistingRequests();
        },
        (error) => console.error('Error fetching recommendations:', error)
      );
    }
  }

  filterOutExistingRequests(): void {
    const userInfo = this.authService.getUserInfoFromToken();
    if (userInfo) {
      const userId = userInfo.email;
      this.friendRequestService.getPendingFriendRequests(userId).subscribe(
        (pendingRequests) => {
          const pendingRecipientIds = pendingRequests.map(request => request.recipientId);
          this.recommendations = this.recommendations.filter(
            recommendation => !pendingRecipientIds.includes(recommendation.email)
          );
        },
        (error) => console.error('Error fetching pending friend requests:', error)
      );
    }
  }

  onFriendRequestSent(personEmail: string): void {
    this.recommendations = this.recommendations.filter(person => person.email !== personEmail);
  }
}
