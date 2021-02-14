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
    window.location.href = 'http://localhost:5000/login';
    return false;
  }

}
