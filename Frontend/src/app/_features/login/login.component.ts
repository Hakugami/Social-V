import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators, ReactiveFormsModule, FormBuilder} from '@angular/forms';
import {NgIf} from "@angular/common";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {RegisterDTO} from "../../_models/register.model";
import {LoginModel} from "../../_models/login.model";
import {AuthService} from "../../_services/auth.service";
import {NotificationService} from "../../_services/notification.service";
import {PublicUserModel} from "../../shared/PublicUserModel";
import { UserModelDTO } from '../../_models/usermodel.model';
import { ProfileService } from '../../_services/profile.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, HttpClientModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = {} as FormGroup;

  constructor(private notificationService : NotificationService,private authService: AuthService,private http:HttpClient,private router: Router,private profileService:ProfileService) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, Validators.required)
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const loginModel: LoginModel = {
        email: this.loginForm.get('email')?.value,
        password: this.loginForm.get('password')?.value
      };
      this.authService.login(loginModel).subscribe(response => {

        PublicUserModel.email = this.loginForm.get('email')?.value;
        this.authService.handleLoginResponse(response);
        this.router.navigate(['home']);
      }, error => {
        console.log(error);
      });
    }
  }




}
