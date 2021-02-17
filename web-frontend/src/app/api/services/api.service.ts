/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class ApiService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation apiExchangeTokenGet
   */
  static readonly ApiExchangeTokenGetPath = '/api/exchangeToken';

  /**
   * Returns a jwt token.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `apiExchangeTokenGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiExchangeTokenGet$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ApiExchangeTokenGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: 'text/plain'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * Returns a jwt token.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `apiExchangeTokenGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiExchangeTokenGet(params?: {
  }): Observable<string> {

    return this.apiExchangeTokenGet$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

}
