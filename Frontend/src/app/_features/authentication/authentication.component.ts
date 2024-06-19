import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {LoginComponent} from "../login/login.component";

@Component({
  selector: 'app-authentication',
  standalone: true,
  imports: [
    RouterOutlet,
    LoginComponent
  ],
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.css'
})
export class AuthenticationComponent {

}
