import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = localStorage.getItem('token');
    if (token) {
      const authReq = req.clone({
        setHeaders: {Authorization:`Bearer ${token}`}
      })
      console.log('AuthInterceptor ' + authReq.headers.get('Authorization'));
      return next.handle(authReq)
    } else {
      return next.handle(req);
    }
  }
}
