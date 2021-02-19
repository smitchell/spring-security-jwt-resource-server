import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private httpClient: HttpClient) {
  }

  exchangeToken(authenticationCode: string): Observable<any> {
    console.log(`AuthenticationService exchangeToken <-- ${authenticationCode}`);
    return this.httpClient
      .get('http://localhost:5005/authenticate/' + authenticationCode, this.httpOptions);
  }

  isUserLoggedIn(): boolean {
    return sessionStorage.getItem('token') !== null;
  }

  logOut(): void {
    sessionStorage.removeItem('token');
  }
}


