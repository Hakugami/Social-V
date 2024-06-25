import { Gender } from "./_enums/Gender.model";
import { Status } from "./_enums/Status.model";



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
