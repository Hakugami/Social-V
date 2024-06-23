import { NgClass } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Friend } from '../../_models/friend.model';
import { UserModelDTO } from '../../_models/userdto.model';
import { DefaultImageDirective } from '../../_directives/default-image.directive';

@Component({
  selector: 'app-friend-status',
  standalone: true,
  imports: [NgClass,DefaultImageDirective],
  templateUrl: './friend-status.component.html',
  styleUrl: './friend-status.component.css'
})
export class FriendStatusComponent {
@Input()
status: string='status-online';
  @Input()
  friend!: UserModelDTO;

}
