import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserModelDTO} from "../../_models/usermodel.model";
import {PublicUserModel} from "../../shared/PublicUserModel";
import { Subscription } from 'rxjs';
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [CommonModule,DefaultImageDirective],
  templateUrl: './profile-header.component.html',
  styleUrl: './profile-header.component.css'
})
export class ProfileHeaderComponent implements OnInit {
  usermodel: UserModelDTO | null = null;
  private userModelSubscription: Subscription | undefined;

  constructor(private publicUserModel: PublicUserModel) {}

  ngOnInit() {
    this.userModelSubscription = this.publicUserModel.userModel$.subscribe(
      (user) => {
        this.usermodel = user;
      }
    );
  }
}
