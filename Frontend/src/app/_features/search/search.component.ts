import { Component } from '@angular/core';
import { UserSearchService } from '../../_services/search.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserModelDTO } from '../../_models/userdto.model';
import { SearchResultsComponent } from '../search-results/search-results.component';
import { Router } from '@angular/router';
import { SearchStateService } from '../../_services/search-state.service';

@Component({
  selector: 'app-user-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, SearchResultsComponent],
})
export class UserSearchComponent {
  searchTerm = '';
  users: UserModelDTO[] = [];
  selectedUser: any;

  constructor(
    private userSearchService: UserSearchService,
    private router: Router,
    private searchStateService: SearchStateService
  ) {}

  onSearch(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      event.preventDefault();
      this.performSearch();
    }
  }

  performSearch() {
    if (this.searchTerm.trim()) {
      this.userSearchService.searchUsers(this.searchTerm).subscribe(users => {
        this.users = users;
        console.log('Users found: ----------------------------');
        console.log(this.users);
        this.searchStateService.setSearchResults(this.users);
        this.navigateToResults();
      });
    }
  }

  navigateToResults() {
    this.router.navigate(['/search-results']);
  }

  onSelect(username: string) {
    this.userSearchService.getUserByUsername(username).subscribe(user => {
      this.selectedUser = user;
    });
  }
}