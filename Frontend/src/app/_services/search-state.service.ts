import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserModelDTO } from '../_models/usermodel.model';

@Injectable({
  providedIn: 'root'
})
export class SearchStateService {
  private searchResultsSource = new BehaviorSubject<UserModelDTO[]>([]);
  searchResults$ = this.searchResultsSource.asObservable();

  setSearchResults(results: UserModelDTO[]) {
    this.searchResultsSource.next(results);
  }
}
