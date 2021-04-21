import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'environments/environment';
import { HttpUtilService } from '@core/services/http-util.service';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { take } from 'rxjs/operators';

const ROTAS = {
  usuario: '/usuario',
  selecao: 'selecao',
}
@Injectable()
export class UsuarioService {

  readonly url = environment.apiUrl;
  constructor(private httpClient: HttpClient, private httpUtil: HttpUtilService) { }

  recuperarUsuario(username: string): Observable<Keycloak.KeycloakProfile> {
    return this.httpClient.get(`${this.url}${ROTAS.usuario}`, {
      params: this.httpUtil.httpParamsByObjeto({username: username})
    });
  }

  cadastrarUsuario(profile: Keycloak.KeycloakProfile): Observable<AppResponsePadraoModel> {
    return this.httpClient.post<any>(`${this.url}${ROTAS.usuario}`, profile);
  }

  checkin(): Observable<AppResponsePadraoModel> {
    return this.httpClient.post<any>(`${this.url}${ROTAS.usuario}/checkin`, {});
  }

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.httpClient.get<SelecaoDTO[]>(`${this.url}${ROTAS.usuario}/${ROTAS.selecao}`).pipe(take(1));
  }
}
