import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

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
