import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { UserModelDTO } from '../_models/usermodel.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserSearchService {
  private apiUrl = environment.searchApiUrl;
  private searchTerms = new BehaviorSubject<string>('');
  users$: Observable<any[]>;

  constructor(private http: HttpClient) {
    this.users$ = this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => this.searchUsers(term))
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  searchUsers(term: string): Observable<UserModelDTO[]> {
    const body = new HttpParams().set('query', term);
    
    return this.http.get<UserModelDTO[]>(`${this.apiUrl}/autocomplete`, {
      params: body,
      observe: 'body',
      responseType: 'json'
    });
  }

  getUserByUsername(username: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/by-username/${username}`);
  }
}