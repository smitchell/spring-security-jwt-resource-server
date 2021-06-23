import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './pages/home/home.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {LogoutComponent} from './pages/logout/logout.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HeaderComponent} from './components/header/header.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MatInputModule} from '@angular/material/input';
import {CanDeactivateGuard} from './directives/can-component-deactivate';
import {TokenInterceptor} from './components/auth/token.interceptor';
import {AuthorizedComponent} from './pages/authorized/authorized.component';
import {ErrorComponent} from './pages/error/error.component';
import {CookieService} from 'ngx-cookie-service';
import {ToastrModule} from 'ngx-toastr';
import { LoginComponent } from './pages/login/login.component';
import {EnvironmentsModule} from "../environments/environment-variables.module";

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
    MatToolbarModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    ToastrModule.forRoot()
  ],
  providers: [
    CanDeactivateGuard,
    CookieService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
