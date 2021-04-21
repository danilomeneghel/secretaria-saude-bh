import { Injectable } from '@angular/core';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private keycloakService: KeycloakService) {
  }

  possuiPermissao(permissoes: PermissaoUsuarioEnum | PermissaoUsuarioEnum[]): boolean {
    permissoes = permissoes instanceof Array ? permissoes : [permissoes];

    if (this.keycloakService.isLoggedIn()) {
      return permissoes.some(permissao => this.keycloakService.getUserRoles().includes(permissao));
    }

    return false;
  }
}
