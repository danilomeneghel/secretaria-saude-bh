import { Injectable } from '@angular/core';
import { AutenticacaoModel } from '@shared/models/interface/autenticacao.model';
import { CredenciaisModel } from '@shared/models/interface/credenciais.model';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { PublicModule } from '../public.module';

@Injectable({
  providedIn: PublicModule
})
export class LoginService {

  constructor() { }

  relizarLogin(_credenciais: CredenciaisModel): Observable<AutenticacaoModel> {
    return Observable.create(obs => {
      const authModel: AutenticacaoModel = {
        tipo: 'TEST_TYPE',
        token: 'TEST_TOKEN'
      };
      obs.next(authModel);
    }).pipe(
      take(1)
    );
  }
}
