import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../api/services';
import {GatewayMessage} from '../../api/models';
import {HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  gatewayMessage: GatewayMessage;
  busy: boolean;

  constructor(private apiService: ApiService,
              private toastr: ToastrService,
              private router: Router
  ) {
  }

  ngOnInit(): void {}

  onGatewayMessage(): void {
    this.busy = true;
    this.gatewayMessage = undefined;
    this.apiService.apiGatewayMessageGet()
      .subscribe(
        res => {
          this.busy = false;
          this.gatewayMessage = res;
        },
        error => {
          this.handleError(error);
        }
      );
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
            this.router.navigateByUrl('/');
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
