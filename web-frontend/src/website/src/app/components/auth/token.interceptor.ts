import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // All HTTP requests are going to go through this method
    const token = sessionStorage.getItem('token');
    if (token) {
      const request: HttpRequest<any> = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
      return next.handle(request);
    }
    return next.handle(req);
  }
}
