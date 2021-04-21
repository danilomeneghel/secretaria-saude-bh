import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { FswServicoHttpPadraoModel } from '@shared/models/interface/fsw-crud/fsw-servico-http-padrao.model';

@Injectable({
  providedIn: 'root'
})
export abstract class FswHttpService implements Partial<FswServicoHttpPadraoModel> {
  abstract tempFiltroPesquisa;
  abstract moduleParentRouter: string;

  protected constructor(router: Router) {
    router.events.subscribe(events => {
      if (events instanceof NavigationEnd && this.moduleParentRouter.split('/')[2] !== events.url.split('/')[2]) {
        this.tempFiltroPesquisa = undefined;
      }
    });
  }
}
