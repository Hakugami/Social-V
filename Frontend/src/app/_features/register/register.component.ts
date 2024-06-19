import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors, ValidatorFn} from '@angular/forms';
import {RegisterDTO} from "../../_models/register.model";
import { ActivatedRoute } from '@angular/router';
import {NgIf} from "@angular/common";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { debounceTime } from 'rxjs/operators';


export function passwordValidator(): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) {
      return null;
    }
    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumeric = /[0-9]/.test(value);
    const hasSymbol = /\W/.test(value);
    const isLengthValid = value.length >= 8 && value.length <= 12;

    if (!hasUpperCase) {
      return { missingUpperCase: true };
    }
    if (!hasLowerCase) {
      return { missingLowerCase: true };
    }
    if (!hasNumeric) {
      return { missingNumeric: true };
    }
    if (!hasSymbol) {
      return { missingSymbol: true };
    }
    if (!isLengthValid) {
      return { lengthInvalid: true };
    }
    return null;
  };
}

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  registerForm: FormGroup = {} as FormGroup;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private http: HttpClient) { }


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
        this.http.get<boolean>('http://localhost:8081/api/v1/users/checkFullName', { params: { fullName: value } }).subscribe(isTaken => {
          if (isTaken)
            this.registerForm.get('fullName')?.setErrors({ 'taken': true });
        });
      }
    });

    this.registerForm.get('email')?.valueChanges.pipe(
      debounceTime(2000)  // Wait for 2 seconds of inactivity
    ).subscribe(value => {
      if (value) {
        this.http.get<boolean>('http://localhost:8081/api/v1/users/checkEmail', { params: { email: value } }).subscribe(isTaken => {
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
      this.http.post('http://localhost:8081/api/v1/users/register', registerDTO).subscribe(response => {
        console.log(response);
        // Handle the response here
      }, error => {
        console.log(error);
        // Handle the error here
      });
    }
  }
}
