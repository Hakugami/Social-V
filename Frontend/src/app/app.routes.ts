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
import {ChatPageComponent} from "./_features/chat-page/chat-page.component";
import {ChatWindowComponent} from "./_features/chat-window/chat-window.component";
// import { ChatMessageComponent } from "./_features/chat-message/chat-message.component";
// import { ChatPageComponent } from "./_features/chat-page/chat-page.component";
// import { ChatWindowComponent } from "./_features/chat-window/chat-window.component";
// import { DirectMessageMembersComponent } from "./_features/direct-message-members/direct-message-members.component";


export const routes: Routes = [
  {
    path: 'profile/edit',
    canActivate: [authGuard],
    component: ProfileEditComponent
  },
  {
    path: 'profile/:username',
    canActivate: [authGuard],
    component: ProfilePageComponent
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
    path: 'friend-request', component: FriendRequestPageComponent, canActivate: [authGuard]
  },
  {
    path: 'chat',
    component: ChatPageComponent,
    canActivate: [authGuard]
  },
  {
    path: 'chat/:username', component: ChatPageComponent, canActivate: [authGuard]
  },
  {
    path: 'search-results', component: SearchResultsComponent, canActivate: [authGuard]
  },
  {path: 'search-results', component: SearchResultsComponent , canActivate: [authGuard]},

  {path: '**', redirectTo: '/home'}


];
