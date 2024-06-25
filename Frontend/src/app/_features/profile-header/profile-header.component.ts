import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
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
export class ProfileHeaderComponent implements OnChanges {
  @Input() user: UserModelDTO | null = null;
  @Input() isOwnProfile: boolean = false
  private userModelSubscription: Subscription | undefined;


  ngOnChanges(changes: SimpleChanges): void {
  }
}
