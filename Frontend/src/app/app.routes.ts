import {Routes} from '@angular/router';
import {ProfilePageComponent} from './_features/profile-page/profile-page.component';
import {ProfileEditComponent} from "./_features/profile-edit/profile-edit.component";
import {NewsFeedComponent} from './_features/news-feed/news-feed.component';
import {LoginComponent} from './_features/login/login.component';
import {RegisterComponent} from "./_features/register/register.component";
import {AuthenticationComponent} from "./_features/authentication/authentication.component";
import {FriendRequestPageComponent} from './_features/friend-request-page/friend-request-page.component';
import {authGuard} from "./_guards/auth.guard";
import {SearchResultsComponent} from './_features/search-results/search-results.component';
import {FriendProfilePageComponent} from './_features/friend-profile-page/friend-profile-page.component';
import {UserMatchGuard} from "./_guards/UserMatch.guard";


export const routes: Routes = [
  {
    path: 'profile/:username',
    canActivate: [authGuard],
    component: ProfilePageComponent
  },
  {
    path: 'profile/edit',
    canActivate: [authGuard],
    component: ProfileEditComponent
  },
  {
    path: '',
    canActivate: [authGuard],
    component: NewsFeedComponent
  },
  {
    path: 'home',
    canActivate: [authGuard],
    component: NewsFeedComponent
  }
  // , {path: 'profile/:username', component: FriendProfilePageComponent}
  ,
  {
    path: 'auth',
    component: AuthenticationComponent,
    children: [
      {path: '', redirectTo: 'login', pathMatch: 'full'},
      {path: 'login', component: LoginComponent},
      {path: 'register', component: RegisterComponent},
    ]
  },
  {
    path: 'friend-request', component: FriendRequestPageComponent
  },
  {
    path: 'profile/:username', component: ProfilePageComponent
  }
  ,
  {path: 'search-results', component: SearchResultsComponent},

  {path : '**' , redirectTo: '/home'}


];
