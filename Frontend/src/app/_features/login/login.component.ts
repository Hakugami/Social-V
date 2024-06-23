import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators, ReactiveFormsModule, FormBuilder} from '@angular/forms';
import {NgIf} from "@angular/common";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {RegisterDTO} from "../../_models/register.model";
import {LoginModel} from "../../_models/login.model";
import {AuthService} from "../../_services/auth.service";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, HttpClientModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = {} as FormGroup;

  constructor(private authService: AuthService,private http:HttpClient,private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, Validators.required)
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      console.log(`email : ${this.loginForm.get('email')?.value}`);
      console.log(`password : ${this.loginForm.get('password')?.value}`);
      const loginModel: LoginModel = {
        email: this.loginForm.get('email')?.value,
        password: this.loginForm.get('password')?.value // Corrected typo here
      };
      this.authService.login(loginModel).subscribe(response => {
        console.log(response);
        const jwtToken = response.jwtToken;
        const refreshToken = response.refreshToken;
        localStorage.setItem('token', jwtToken);
        localStorage.setItem('refresh', refreshToken);
        this.router.navigate(['home']);
      }, error => {
        console.log(error);
      });
    }
  }

}
