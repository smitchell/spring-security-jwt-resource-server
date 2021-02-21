import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
  ) {}

  public isAuthenticated(): boolean {
    const tokens: string = sessionStorage.getItem('token');
    if (tokens) {
      try {
        return true;
        // put refresh token logic here
      } catch (e) {
        console.error('Access token error', e);
        localStorage.removeItem('token');
      }
    }
    return false;

  }

}
