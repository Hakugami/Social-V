import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { BirthdayItem } from '../_models/birthday.model';

@Injectable({
  providedIn: 'root'
})
export class BirthdayService {

    //ill make it call an api later

  private birthdays: BirthdayItem[] = [
    { id: 1, name: 'Anna Sthesia', image: '../assets/images/user/01.jpg', date: 'Today' },
    { id: 2, name: 'Paul Molive', image: '../assets/images/user/02.jpg', date: 'Tomorrow' },
    { id: 3, name: 'Gail Forcewind', image: '../assets/images/user/03.jpg', date: 'Next Week' }
  ];

  getBirthdays(): Observable<BirthdayItem[]> {
    return of(this.birthdays);
  }
}