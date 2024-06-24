import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserModelDTO} from "../../_models/usermodel.model";
import {PublicUserModel} from "../../shared/PublicUserModel";

@Component({
  selector: 'app-profile-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './profile-header.component.html',
  styleUrl: './profile-header.component.css'
})
export class ProfileHeaderComponent implements OnInit {
  userModel: UserModelDTO | undefined;

  constructor(private publicUserModel: PublicUserModel) {}

  ngOnInit() {
    this.userModel = this.publicUserModel.getUserModel();
  }
}
