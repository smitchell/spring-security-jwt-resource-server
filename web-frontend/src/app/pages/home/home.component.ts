import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../api/services';
import {GatewayMessage} from '../../api/models';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  gatewayMessage: GatewayMessage;
  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
  }

  onGatewayMessage(): void {
    this.apiService.apiGatewayMessageGet()
      .subscribe(
        res => {
          this.gatewayMessage = res;
        },
        error => {
          console.error(error);
        }
      );
  }

}
