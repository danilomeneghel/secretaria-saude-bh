import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivePublicModule } from './guards/public-guard';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';

const routes: Routes = [
  {
    path: 'hubsmsa-servicos',
    component: HomeComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent,
        canActivate: [CanActivePublicModule]
      },
    ],
    data: {
      titulo: 'Home'
    },
    canActivate: [CanActivePublicModule],
    canActivateChild: [CanActivePublicModule],
    canLoad: [CanActivePublicModule]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
