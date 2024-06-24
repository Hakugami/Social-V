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
export interface UserModelDTO {
  username: string;
  email: string;
  status: Status | null;
  firstName: string | null;
  lastName: string | null;
  address: string | null;
  gender: Gender | null;
  country: string | null;
  city: string | null;
  birthDate: string | null;  // Using string for date to match JSON serialization
  phoneNumber: string | null;
  profilePicture: string | null;
  coverPicture: string | null;
  url: string | null;
}
