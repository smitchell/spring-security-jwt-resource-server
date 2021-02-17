import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
  ) {}

  public isAuthenticated(): boolean {
    const tokens: string = sessionStorage.getItem('token');
    if (tokens) {
      try {
        return true;
        // const oauth2Tokens: OAuth2Tokens = JSON.parse(tokens)
        // if (this.jwtHelper.isTokenExpired(oauth2Tokens.access_token)) {
        //   console.warn('OAuth Access Token expired')
        //   return false;
          // Bing started returning empty data "{}" so this code needs to be changed to match Ping's expectations.
          // console.info('Renewing expired token for ' + localStorage.getItem('principal'))
          // this.apiService.apiSecurityRefreshTokenPost({body: {
          //     refresh_token: oauth2Tokens.refresh_token
          //   }}).subscribe(
          //   data => {
          //     if (data && data['access_token']) {
          //         console.info('Token refreshed')
          //       const access_token: string = data['access_token']
          //       console.info(`New access token ${access_token}`)
          //       localStorage.setItem('access_token', access_token);
          //       oauth2Tokens.access_token = access_token;
          //       oauth2Tokens.expires_in = data['expires_in']
          //       localStorage.setItem(oauth2TokensKey, JSON.stringify(data));
          //       console.info('Token successfully renewed for ' + localStorage.getItem('principal'))
          //       return true;
          //     } else {
          //       console.error(`Refresh token returned ${data}`);
          //       this.router.navigate(['error-msg'], { queryParams: {title: 'Security Error',
          //       message: 'CHS Single Sign On did not return an access token. Please logout and try again.'}});
          //     }
          //   },
          //   e => {
          //     console.info('Unable to renew Token for ' + localStorage.getItem('principal') + ' ' + e)
          //     localStorage.removeItem(oauth2TokensKey);
          //     localStorage.removeItem('access_token');
          //     localStorage.removeItem('principal');
          //     return false;
          //   }
          // );
        // } else {
        //   // Token is not expired. You're good.
        //   return true;
        // }
      } catch (e) {
        console.error('Access token error', e);
        localStorage.removeItem('token');
      }
    }
    return false;

  }


}
