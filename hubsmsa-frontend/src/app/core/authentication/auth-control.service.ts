import { Injectable } from '@angular/core';
import { SessionStorageKeyEnum } from '@shared/models/enum/session-storage-key.enum';
import { AutenticacaoModel } from '@shared/models/interface/autenticacao.model';
import { AppSessionStorage } from '@core/authentication/app-session-storage';

@Injectable()
export class AuthControlService {
  private usuarioAutenticado: AutenticacaoModel;

  constructor() {

  }

  isLogged() {
    if (this.usuarioAutenticado == null) {
      this.usuarioAutenticado = AppSessionStorage.recuperarValor(SessionStorageKeyEnum.AUTH_KEY);
    }
    return this.usuarioAutenticado != null;
  }

  setUsuarioLogado(autenticacaoModel: AutenticacaoModel) {
    this.usuarioAutenticado = autenticacaoModel;
    AppSessionStorage.salvarValor(SessionStorageKeyEnum.AUTH_KEY, autenticacaoModel);
  }

  getUsuarioLogado(): AutenticacaoModel {
    if (this.isLogged()) {
      return this.usuarioAutenticado;
    }
    return null;
  }

  logOut(): boolean {
    AppSessionStorage.limparValores([SessionStorageKeyEnum.AUTH_KEY]);
    this.usuarioAutenticado = null;
    return AppSessionStorage.recuperarValor(SessionStorageKeyEnum.AUTH_KEY) === null;
  }
}
