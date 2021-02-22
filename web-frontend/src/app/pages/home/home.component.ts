import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../api/services';
import {GatewayMessage, IntrospectToken} from '../../api/models';
import {HttpErrorResponse} from '@angular/common/http';
import {ToastrService} from 'ngx-toastr';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  gatewayMessage: GatewayMessage;
  introspectToken: IntrospectToken;
  decoded: any;
  busy: boolean;

  constructor(private apiService: ApiService,
              private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    const token = sessionStorage.getItem('token');
    if (token) {
      this.decoded = jwt_decode(token);
      this.decoded.authorities = this.replaceCommas(this.decoded.authorities);
      this.decoded.scope = this.replaceCommas(this.decoded.scope);
    }
  }

  replaceCommas(value: any): string {
    if (Array.isArray(value)) {
      return value.join(', ');
    }
    return value;
  }

  onGatewayMessage(): void {
    if (this.gatewayMessage) {
      this.gatewayMessage = undefined;
    } else {
      this.busy = true;
      this.apiService.apiGatewayMessageGet()
        .subscribe(
          res => {
            this.busy = false;
            this.gatewayMessage = res;
          },
          error => {
            this.busy = false;
            this.handleError(error);
          }
        );
    }
  }

  onIntrospectToken(): void {
    if (this.introspectToken) {
      this.introspectToken = undefined
    } else {
      this.busy = true;
      this.apiService.apiIntrospectTokenGet()
        .subscribe(
          res => {
            this.busy = false;
            this.introspectToken = res;
          },
          error => {
            this.busy = false;
            this.handleError(error);
          }
        );
    }
  }

  handleError(error: any): void {
    this.busy = false;
    if (error instanceof HttpErrorResponse) {
      if (error.error instanceof ErrorEvent) {
        console.error('Error Event');
      } else {
        console.log(`error status : ${error.status} ${error.statusText}`);
        switch (error.status) {
          case 401:      // UNAUTHORIZED - Route them hope so the guard condition is re-appliced.
            this.toastr.error('Your session has timed out. Please logout and sign back in.', 'UNAUTHORIZED');
            break;
          case 403:     // FORBIDDEN
            this.toastr.error('You lack the required permissions for this resource.', 'FORBIDDEN');
            break;
        }
      }
    } else {
      this.toastr.error('There was an error fetching the Gateway message.', 'ERROR');
    }
  }
}
