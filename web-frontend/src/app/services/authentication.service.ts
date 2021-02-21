import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import jwt_decode from 'jwt-decode';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  // httpOptions = {
  //   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  // };

  constructor() {
  }

  // exchangeToken(authenticationCode: string): Observable<any> {
  //   console.log(`AuthenticationService exchangeToken <-- ${authenticationCode}`);
  //   return this.httpClient
  //     .get('http://localhost:5005/authenticate/' + authenticationCode, this.httpOptions);
  // }

  isUserLoggedIn(): boolean {
    return sessionStorage.getItem('token') !== null;
  }

  getName(): any {
    const token = sessionStorage.getItem('token');
    if (token) {
      const decoded: any = jwt_decode(token);
      return decoded.sub;
    }
    return null;
  }

}


