import { SessionStorageKeyEnum } from '@shared/models/enum/session-storage-key.enum';

export class AppSessionStorage {

  static recuperarValor<T>(chave: SessionStorageKeyEnum): T {
    if (sessionStorage.getItem(chave) != null) {
      return JSON.parse(sessionStorage.getItem(chave));
    }
    return null;
  }

  static salvarValor(chave: SessionStorageKeyEnum, objeto: any) {
    if (objeto != null) {
      sessionStorage.setItem(chave, JSON.stringify(objeto));
    }
  }

  static limparValores(chave: SessionStorageKeyEnum[]) {
    chave.forEach(key => sessionStorage.removeItem(key));
  }

  static limparSessao() {
    sessionStorage.clear();
  }
}
