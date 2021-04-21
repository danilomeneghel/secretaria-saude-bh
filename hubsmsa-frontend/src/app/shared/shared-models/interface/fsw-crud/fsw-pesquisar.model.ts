import { PaginaResultadoPiweb, PiwebPaginaEvt, TabelaPiwebOrdemEvtModel } from '@shared/modules/modules/piweb-tabela';

export interface FswPesquisarModel {
  resultadoPaginaAtual: PaginaResultadoPiweb<any>;
  paginaAtualCfg: PiwebPaginaEvt;

  btnPesquisar(pagina?: PiwebPaginaEvt): void;

  criarFormulario(): void;

  limpar(): void;

  ordenar(ordem: TabelaPiwebOrdemEvtModel): void;
}
