import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ApiService} from '../../api/services/api.service';
import {JwtToken} from '../../api/models/jwt-token';

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.scss']
})
export class AuthorizedComponent implements OnInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.exchangeToken(params.code, params.state);
    });
  }

  exchangeToken(code: string, state: string): void {
    const expectedState =  localStorage.getItem('auth_code_state');
    localStorage.removeItem('auth_code_state')
    console.log(`exchangeToken <--- code = "${code}" state = "${state}"`);
    if (expectedState !== state) {
      this.handleError({message: 'Invalid authorization code state'})
    }
    if (!code) {
      this.handleError({message: 'Authorization code is missing'})
    }
    const params = {authorizationCode: code, state: state};
    console.log('calling apiService.authenticateGet');

    this.apiService.apiExchangeTokenGet(params).subscribe(
      success => this.handleUserData(success),
      error => this.handleError(error)
    );
  }

  handleError(error: any): void {
    console.log('handleError() ' + error.message);
    console.log(error);
    let errorMsg = 'An error occurred exchanging the authentication code for an access token: ';
    try {
      errorMsg += encodeURIComponent(JSON.stringify(error.message));
    } catch (e) {
      console.error(e);
    }
    this.router.navigate(['error'], {queryParams: {errorMsg}});
  }

  handleUserData(jwtToken: JwtToken): void {
    if (jwtToken && jwtToken.access_token && jwtToken.access_token.length > 0) {
      console.log('Authorization code successfully exchanged for an access token');
      const tokenStr = jwtToken.access_token;
      sessionStorage.setItem('token', tokenStr);
    } else {
      sessionStorage.removeItem('token');
    }
    this.router.navigate(['/']);
  }

}
