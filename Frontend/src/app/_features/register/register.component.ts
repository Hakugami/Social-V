import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';
import {RegisterDTO} from "../../_models/register.model";
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {NgIf} from "@angular/common";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { debounceTime } from 'rxjs/operators';
import {AuthService} from "../../_services/auth.service";
import {passwordValidator} from "../../_utils/password.validator";




@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, HttpClientModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  registerForm: FormGroup = {} as FormGroup;

  constructor(private authService: AuthService,private formBuilder: FormBuilder, private route: ActivatedRoute, private http: HttpClient ,private router: Router) { }


  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, passwordValidator()]],
      terms: [false, Validators.requiredTrue]
    });

    this.registerForm.get('fullName')?.valueChanges.pipe(
      debounceTime(2000)  // Wait for 2 seconds of inactivity
    ).subscribe(value => {
      if (value) {
        this.authService.checkFullName(value).subscribe(isTaken => {
          if (isTaken)
            this.registerForm.get('fullName')?.setErrors({ 'taken': true });
        });
      }
    });

    this.registerForm.get('email')?.valueChanges.pipe(
      debounceTime(2000)  // Wait for 2 seconds of inactivity
    ).subscribe(value => {
      if (value) {
        this.authService.checkEmail(value).subscribe(isTaken => {
          if (isTaken)
            this.registerForm.get('email')?.setErrors({ 'taken': true });
        });
      }
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const registerDTO: RegisterDTO = {
        username: this.registerForm.get('fullName')?.value,
        email: this.registerForm.get('email')?.value,
        password: this.registerForm.get('password')?.value
      };

      //console.log(registerDTO);
      this.authService.register(registerDTO).subscribe(response => {
        console.log(response);
        this.router.navigate(['/auth/login']);
        // Handle the response here
      }, error => {
        console.log(error);
        // Handle the error here
      });
    }
  }
}
