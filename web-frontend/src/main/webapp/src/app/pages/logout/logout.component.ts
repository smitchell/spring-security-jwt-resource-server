import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CookieService} from 'ngx-cookie-service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private router: Router,
              private http: HttpClient,
              private cookieService: CookieService) {
  }

  ngOnInit(): void {
    this.logout();
  }

  logout(): void {
    console.log('Perform logout');
    sessionStorage.clear();
    localStorage.clear();
    // this.cookieService.delete('JSESSIONID');
    this.cookieService.deleteAll();
    const url = 'http://localhost:8090/logout';
    window.location.href = 'http://localhost:8090/logout';

  }
}
