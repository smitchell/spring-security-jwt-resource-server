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

import { GatewayMessage } from '../models/gateway-message';
import { IntrospectToken } from '../models/introspect-token';
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
  apiExchangeTokenGet$Response(params: {
    authorizationCode: string;
  }): Observable<StrictHttpResponse<JwtToken>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ApiExchangeTokenGetPath, 'get');
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
   * To access the full response (for headers, for example), `apiExchangeTokenGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiExchangeTokenGet(params: {
    authorizationCode: string;
  }): Observable<JwtToken> {

    return this.apiExchangeTokenGet$Response(params).pipe(
      map((r: StrictHttpResponse<JwtToken>) => r.body as JwtToken)
    );
  }

  /**
   * Path part for operation apiIntrospectTokenGet
   */
  static readonly ApiIntrospectTokenGetPath = '/api/introspectToken';

  /**
   * Returns the token introspection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `apiIntrospectTokenGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiIntrospectTokenGet$Response(params?: {
  }): Observable<StrictHttpResponse<IntrospectToken>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ApiIntrospectTokenGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<IntrospectToken>;
      })
    );
  }

  /**
   * Returns the token introspection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `apiIntrospectTokenGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiIntrospectTokenGet(params?: {
  }): Observable<IntrospectToken> {

    return this.apiIntrospectTokenGet$Response(params).pipe(
      map((r: StrictHttpResponse<IntrospectToken>) => r.body as IntrospectToken)
    );
  }

  /**
   * Path part for operation apiGatewayMessageGet
   */
  static readonly ApiGatewayMessageGetPath = '/api/gatewayMessage';

  /**
   * Returns the gateway information.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `apiGatewayMessageGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiGatewayMessageGet$Response(params?: {
  }): Observable<StrictHttpResponse<GatewayMessage>> {

    const rb = new RequestBuilder(this.rootUrl, ApiService.ApiGatewayMessageGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<GatewayMessage>;
      })
    );
  }

  /**
   * Returns the gateway information.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `apiGatewayMessageGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  apiGatewayMessageGet(params?: {
  }): Observable<GatewayMessage> {

    return this.apiGatewayMessageGet$Response(params).pipe(
      map((r: StrictHttpResponse<GatewayMessage>) => r.body as GatewayMessage)
    );
  }

}
