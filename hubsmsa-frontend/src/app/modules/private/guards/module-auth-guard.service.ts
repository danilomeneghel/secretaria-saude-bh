import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { PrivateModule } from '../private.module';
import { SecurityService } from '@core/authentication/security.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: PrivateModule
})
export class ModuleAuthGuard extends KeycloakAuthGuard {
  constructor(
    private securityService: SecurityService,
    private alertService: NgcAlertService,
    protected router: Router,
    protected keycloakAngular: KeycloakService,
  ) {
    super(router, keycloakAngular);
  }

  isAccessAllowed(route: ActivatedRouteSnapshot): Promise<boolean> {
    return new Promise(async resolve => {
      if (!this.authenticated) {
        this.keycloakAngular.login();
        return;
      }

      let possuiPermissao: boolean;

      if (route.data) {
        const permissoes = route.data.permissoesNecessarias;
        possuiPermissao = this.securityService.possuiPermissao(permissoes);

        if (!possuiPermissao) {
          this.alertService.error(MensagemEnum.NAO_POSSUI_PERMISSAO);
        }

      } else {
        possuiPermissao = true;
      }

      resolve(possuiPermissao);

    });
  }
}
