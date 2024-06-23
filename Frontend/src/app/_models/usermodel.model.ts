// Gender enum
export enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE'
}

// Status enum
export enum Status {
  SINGLE = 'SINGLE',
  MARRIED = 'MARRIED',
  DIVORCED = 'DIVORCED',
  WIDOWED = 'WIDOWED',
  SEPARATED = 'SEPARATED'
}

// UserModelDto interface
export interface UserModel {
  username: string;
  email: string;
  status: Status;
  firstName: string;
  lastName: string;
  address: string;
  gender: Gender;
  country: string;
  city: string;
  birthDate: string;  // Using string for date to match JSON serialization
  phoneNumber: string;
  profilePicture: string;
  coverPicture: string;
  url: string;
}
