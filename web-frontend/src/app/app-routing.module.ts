import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LogoutComponent} from './pages/logout/logout.component';
import {AuthGuardService} from './components/auth/auth-guard.service';
import {AuthorizedComponent} from './pages/authorized/authorized.component';
import {ErrorComponent} from './pages/error/error.component';

const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuardService]},
  { path: 'authorized', component: AuthorizedComponent },
  { path: 'logout', component: LogoutComponent },
  { path: 'error', component: ErrorComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
