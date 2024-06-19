import { Routes } from '@angular/router';
import { ProfilePageComponent } from './_features/profile-page/profile-page.component';
import { ProfileEditComponent } from "./_features/profile-edit/profile-edit.component";
import { NewsFeedComponent } from './_features/news-feed/news-feed.component';

export const routes: Routes = [
  { path: 'profile', component: ProfilePageComponent },
  { path: 'profile/edit', component: ProfileEditComponent},
  {path:'',component:NewsFeedComponent},
  {path:'home',component:NewsFeedComponent},
];
