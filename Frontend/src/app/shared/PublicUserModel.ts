import { Injectable } from '@angular/core';
import {UserModelDTO} from "../_models/usermodel.model";

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

  constructor() { }

  // Optionally, you can add methods to manipulate the object
  getUserModel() {
    return PublicUserModel.user_model;
  }

  getEmail(){
    return PublicUserModel.email;
  }

  setUserModel(newModel: UserModelDTO) {
    PublicUserModel.user_model = newModel;
  }

  setEmail(newEmail:string){
    PublicUserModel.email=newEmail;
  }
}
