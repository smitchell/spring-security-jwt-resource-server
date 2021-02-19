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

import { BuildInfo } from '../models/build-info';
import { JwtToken } from '../models/jwt-token';

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
   * Path part for operation exchangeTokenGet
   */
  static readonly ExchangeTokenGetPath = '/exchangeToken';

  /**
   * Returns a jwt token.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `exchangeTokenGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  exchangeTokenGet$Response(params: {
    authorizationCode: string;
  }): Observable<StrictHttpResponse<JwtToken>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ExchangeTokenGetPath, 'get');
    if (params) {
      rb.query('authorizationCode', params.authorizationCode, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<JwtToken>;
      })
    );
  }

  /**
   * Returns a jwt token.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `exchangeTokenGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  exchangeTokenGet(params: {
    authorizationCode: string;
  }): Observable<JwtToken> {

    return this.exchangeTokenGet$Response(params).pipe(
      map((r: StrictHttpResponse<JwtToken>) => r.body as JwtToken)
    );
  }

  /**
   * Path part for operation apiBuildInfoGet
   */
  static readonly ApiBuildInfoGetPath = '/api/buildInfo';

  /**
   * Returns the build information.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `apiBuildInfoGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiBuildInfoGet$Response(params?: {
  }): Observable<StrictHttpResponse<BuildInfo>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ApiBuildInfoGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<BuildInfo>;
      })
    );
  }

  /**
   * Returns the build information.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `apiBuildInfoGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiBuildInfoGet(params?: {
  }): Observable<BuildInfo> {

    return this.apiBuildInfoGet$Response(params).pipe(
      map((r: StrictHttpResponse<BuildInfo>) => r.body as BuildInfo)
    );
  }

}
