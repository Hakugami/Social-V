import { Routes } from '@angular/router';
import { ProfilePageComponent } from './_features/profile-page/profile-page.component';
import { ProfileEditComponent } from "./_features/profile-edit/profile-edit.component";
import { NewsFeedComponent } from './_features/news-feed/news-feed.component';
import { LoginComponent } from './_features/login/login.component';
import { RegisterComponent } from "./_features/register/register.component";
import { AuthenticationComponent } from "./_features/authentication/authentication.component";


export const routes: Routes = [
  { path: 'profile', component: ProfilePageComponent },
  { path: 'profile/edit', component: ProfileEditComponent},
  {path:'',component:NewsFeedComponent},
  {path:'home',component:NewsFeedComponent},
  {
    path: 'auth',
    component: AuthenticationComponent,
    children: [
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
    ]
  },

];
