import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  loginUrl: string;

  constructor(
    public auth: AuthService,
    public router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    localStorage.setItem('canActivate', state.url);

    if (this.auth.isAuthenticated()) {
      return true;
    }
    // See https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/provider/endpoint/TokenEndpoint.html
    // Clients must be authenticated using a Spring Security Authentication to access /oauth/ endpoint,
    // and the client id is extracted from the authentication token. The best way to arrange this (as per the OAuth2 spec)
    // is to use HTTP basic authentication for this endpoint with standard Spring Security support.
    window.location.href = 'http://localhost:5000/login';
    return false;
  }

}
