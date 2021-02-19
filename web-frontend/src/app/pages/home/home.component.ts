import { Component, OnInit } from '@angular/core';
import {BuildInfo} from '../../api/models/build-info';
import {ApiService} from '../../api/services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  buildInfo: BuildInfo;
  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
  }

  onBuildInfo(): void {
    this.apiService.apiBuildInfoGet()
      .subscribe(
        res => {
          this.buildInfo = res;
        },
        error => {
          console.error(error);
        }
      );
  }

}
