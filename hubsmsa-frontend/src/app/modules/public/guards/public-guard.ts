import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, Router } from '@angular/router';
import { AuthControlService } from '@core/authentication/auth-control.service';
import { KeycloakService } from 'keycloak-angular';

@Injectable()
export class CanActivePublicModule implements CanActivate, CanActivateChild {
  constructor(
    private authControl: AuthControlService,
    private router: Router,
    private keycloakService: KeycloakService
  ) {
  }

  canActivate(): boolean {
    return this.verificarERedirecionar();
  }

  canActivateChild(): boolean {
    return this.verificarERedirecionar();
  }

  verificarERedirecionar() {
    if (this.authControl.isLogged()) {
      this.router.navigate(['admin']);
      return false;
    } else {
      this.keycloakService.login().then(() => this.router.navigate(['admin']));
    }
  }
}
