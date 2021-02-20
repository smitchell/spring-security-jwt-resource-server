import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.logout();
  }

  logout(): void {
    console.log('Perform logout');
    sessionStorage.clear();
    console.log('Token is ' + sessionStorage.getItem('token'));
    this.deleteAllCookies();
    this.router.navigate(['/']);
  }

  deleteAllCookies(): void {
    const cookies = document.cookie.split(';');

    for (const cookie of cookies) {
      console.log('cookie ' + cookie);
      const eqPos = cookie.indexOf('=');
      const name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
      document.cookie = name + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT';
    }
  }

}
