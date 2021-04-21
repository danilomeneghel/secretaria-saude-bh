import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { Constantes } from '@shared/models/class/constants';

@Component({
  selector: 'sbapp-painel-administrativo',
  templateUrl: './painel-administrativo.component.html',
  styleUrls: ['./painel-administrativo.component.css']
})
export class PainelAdministrativoComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[];

  constructor(
    private validacaoService: ValidacaoFormularioService,
    private ngcAlert: NgcAlertService
  ) {
  }

  ngOnInit() {
    this.cadastrarEventos();
  }

  cadastrarEventos(): any {
    this.subscriptions = [
      this.validacaoService.$listarErros.subscribe(listaErros => {
        this.ngcAlert.clearAll();
        if (listaErros) {
          listaErros.forEach(erroMsg => this.ngcAlert.error(erroMsg, { noFocus: true }));
        }
      })
    ];
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  getVersao() {
    return Constantes.versaoBuild;
  }

}
