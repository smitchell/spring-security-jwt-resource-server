import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {LogoutComponent} from './pages/logout/logout.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HeaderComponent} from './components/header/header.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {CanDeactivateGuard} from './directives/can-component-deactivate';
import {TokenInterceptor} from './components/auth/token.interceptor';
import {AuthorizedComponent} from './pages/authorized/authorized.component';
import {ErrorComponent} from './pages/error/error.component';
import {CookieService} from 'ngx-cookie-service';
import {ToastrModule} from 'ngx-toastr';
import {LoginComponent} from './pages/login/login.component';
import {EnvironmentsModule} from "../environments/environment-variables.module";
import {MaterialModule} from "./material.module";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LogoutComponent,
    HeaderComponent,
    AuthorizedComponent,
    ErrorComponent,
    LoginComponent,
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    EnvironmentsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    MaterialModule
  ],
  providers: [
    CanDeactivateGuard,
    CookieService,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
