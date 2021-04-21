import { Component, EventEmitter, Input, Output } from '@angular/core';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { PaginaResultadoPiweb, PiwebPaginaEvt, TabelaPiwebOrdemEvtModel } from '@shared/modules/modules/piweb-tabela';

@Component({
  selector: 'historico-alteracao-padrao',
  templateUrl: './historico-alteracao-padrao.component.html',
  styleUrls: ['./historico-alteracao-padrao.component.css']
})
export class HistoricoAlteracaoPadraoComponent {
  @Input() historicoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  @Input() numeroPaginaAtual = 1;
  @Output() novaPaginaEvt = new EventEmitter<PiwebPaginaEvt>();
  @Output() ordenarEvt = new EventEmitter<TabelaPiwebOrdemEvtModel>();

  constructor() {
  }

  novaPagina($event) {
    this.novaPaginaEvt.emit($event);
  }

  ordenar($event) {
    this.ordenarEvt.emit($event);
  }
}
