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
  code: string;
  state: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private apiService: ApiService
  ) {
  }

  private static getArray(attribute: any): string[] {
    if (Array.isArray(attribute)) {
      return attribute;
    }
    return [attribute];
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.code = params.code;
      this.state = params.state;
      /*
        This is a demo workaround. The first time /oauth/authorize is called, it triggers the custom login form,
        but only a sessionID is returned, not an authorization code. The second time it is called, using
        a valid sessionID, the authorization code is returned.
       */
      if (!this.code) {
        const randomState: string = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
        window.location.href = 'http://localhost:5000/oauth/authorize?response_type=code&client_id=gateway-client&state=' + randomState;
        return false;
      } else {
        this.exchangeToken();
      }
    });
  }

  exchangeToken(): void {
    console.log(`code = "${this.code}" state = "${this.state}"`);
    const params = {authorizationCode: this.code};
    console.log('calling apiService.authenticateGet');

    this.apiService.exchangeTokenGet(params).subscribe(
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
    this.router.navigate(['error'],  { queryParams: { errorMsg }});
  }

  handleUserData(jwtToken: JwtToken): void {
    console.log('handleUserData() <--- ' + JSON.stringify(jwtToken));
    if (jwtToken && jwtToken.access_token && jwtToken.access_token.length > 0) {
      const tokenStr = 'Bearer ' + jwtToken.access_token;
      sessionStorage.setItem('token', tokenStr);
    } else {
      sessionStorage.removeItem('token');
    }
    this.router.navigate(['/']);
  }

}
