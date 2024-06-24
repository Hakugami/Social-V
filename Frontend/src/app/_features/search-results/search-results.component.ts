import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserModelDTO } from '../../_models/userdto.model';
import { PersonCardComponent } from "../person-card/person-card.component";
import { NgFor } from '@angular/common';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { SearchStateService } from '../../_services/search-state.service';

@Component({
  selector: 'app-search-results',
  standalone: true,
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css'],
  imports: [PersonCardComponent, NgFor]
})
export class SearchResultsComponent implements OnInit, OnDestroy {
  results: UserModelDTO[] = [];
  private routeSubscription!: Subscription;

  constructor(private router: Router, private route: ActivatedRoute, private searchStateService: SearchStateService) {
    console.log('SearchResultsComponent constructor called');
  }

  ngOnInit() {
    console.log('SearchResultsComponent initialized');
    
    this.routeSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateResults();
      }
    });
    
    // Initial load
    this.updateResults();
  }

  ngOnDestroy() {
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }

  private updateResults() {
    this.searchStateService.searchResults$.subscribe(results => {
      this.results = results;
      console.log(this.results);
  }
    );
  }

  onFriendRequestSent(personEmail: string): void {
    this.results = this.results.filter(person => person.email !== personEmail);
  }
}
