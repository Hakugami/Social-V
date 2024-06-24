import { Injectable } from '@angular/core';
import {UserModelDTO} from "../_models/usermodel.model";
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PublicUserModel {
  // Define your static object with the type UserModelDto
  public static email:string="";

  public static user_model: UserModelDTO = {
    username: '',
    email: '',
    status : null,
    firstName: '',
    lastName: '',
    address: '',
    gender: null,
    country: null,
    city: '',
    birthDate: null,
    phoneNumber: null,
    profilePicture: null,
    coverPicture: null,
    url: null
  };

    private userModelSubject = new BehaviorSubject<UserModelDTO | null>(null);
  userModel$ = this.userModelSubject.asObservable();

  setUserModel(user: UserModelDTO) {
    this.userModelSubject.next(user);
  }

  getUserModel() {
    return this.userModelSubject.getValue();
  }

  constructor() { }



  getEmail(){
    return PublicUserModel.email;
  }

  setEmail(newEmail:string){
    PublicUserModel.email=newEmail;
  }
}



