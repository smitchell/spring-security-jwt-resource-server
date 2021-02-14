import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {
  }

  authenticate(username, password): any {
    const headersObject = new HttpHeaders();
    headersObject.append('Content-Type', 'application/json');
    headersObject.append('Authorization', 'Basic ' + btoa('frontend:client_secret'));
    return this.httpClient
      .post<any>('http://localhost:5000/authenticate', {username, password}, {headers: headersObject})
      .pipe(
        map(userData => {
          sessionStorage.setItem('username', username);
          const tokenStr = 'Bearer ' + userData.token;
          sessionStorage.setItem('token', tokenStr);
          return userData;
        })
      );
  }

  isUserLoggedIn(): boolean {
    const user = sessionStorage.getItem('username');
    return !(user === null);
  }

  logOut(): void {
    sessionStorage.removeItem('username');
  }
}


