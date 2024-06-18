import { Component } from '@angular/core';
import { UpcomingBirthdayItemComponent } from '../upcoming-birthday-item/upcoming-birthday-item.component';
import { BirthdayService } from '../../_services/birthday.service';
import { BirthdayItem } from '../../_models/birthday.model';
import { NgFor } from '@angular/common';

@Component({
    selector: 'app-upcoming-birthday-tab',
    standalone: true,
    templateUrl: './upcoming-birthday-tab.component.html',
    styleUrl: './upcoming-birthday-tab.component.css',
    imports: [UpcomingBirthdayItemComponent,NgFor]
})
export class UpcomingBirthdayTabComponent {
  birthdays : BirthdayItem[] = [];

  constructor(private birthdayService: BirthdayService) { }

  ngOnInit(): void {
    this.loadBirthdays();
  }

  loadBirthdays(): void {
    this.birthdayService.getBirthdays().subscribe(data => this.birthdays = data);
  }

}
