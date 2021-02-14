import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor() { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // All HTTP requests are going to go through this method
        const request: HttpRequest<any> = req.clone({
            setHeaders: {
                Authorization: `Bearer ${localStorage.getItem('accessTokenKey')}`
            }
        });
        return next.handle(request);
    }
}
