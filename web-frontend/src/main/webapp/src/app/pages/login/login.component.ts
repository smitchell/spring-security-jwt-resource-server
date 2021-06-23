import {Component, Inject, OnInit} from '@angular/core';
import {EnvVariables} from "../../../environments/environment-variables.token";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginUrl: string;

  constructor(
    @Inject(EnvVariables) public envVariables,
  ) {

  }

  ngOnInit(): void {
    const authCodeState: string = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
    localStorage.setItem('auth_code_state', authCodeState)
    const redirectUrl =  encodeURI(this.envVariables.signinRedirectUrl);
    this.loginUrl = this.envVariables.loginUrl + '?response_type=code&client_id=gateway-client&state=' + authCodeState + '&redirect_uri=' + redirectUrl;
    this.redirect();
  }

  redirect() {
    window.location.href = this.loginUrl
  }

}
