import { KeycloakService } from 'keycloak-angular';
import { environment } from '../environments/environment';
import { UsuarioService } from './shared/shared-services/usuario.service';

export function initializer(keycloak: KeycloakService, usuarioService: UsuarioService): () => Promise<any> {
  //let user;
  return (): Promise<any> => keycloak.init({      
  config: {
      url: environment.urlKeycloak,
      realm: environment.realm,
      clientId: environment.clientId,
      credentials: {
        secret: environment.jwtSecret
      }
    }
  }).then(() => {
    return keycloak.loadUserProfile(true);
  }).then(() => {
    //return usuarioService.recuperarUsuario(res.username).toPromise();
    return usuarioService.checkin().toPromise();
  }).then((res) => {
    if (res.mensagem.type === 'error') {
      keycloak.logout();
    }
    /*if (!res) {
      usuarioService.cadastrarUsuario(user).subscribe((res) => { console.log('usuario, salvo!', res);
      });
    } else if (res === false) {
      keycloak.logout();
    }*/
  })
  .catch((err) => console.log('err', err));

}
