import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-authorized',
  templateUrl: './authorized.component.html',
  styleUrls: ['./authorized.component.scss']
})
export class AuthorizedComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute
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
      this.login(params);
    });
  }

  login(params: any): void {
    console.log(`token = "${JSON.stringify(params)}`);
  }

}
