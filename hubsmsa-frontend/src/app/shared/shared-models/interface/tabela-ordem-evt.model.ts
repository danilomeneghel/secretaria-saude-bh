import { TipoOrdenacaoEnum } from './../enum/tipo-ordenacao.enum';
export interface TabelaOrdemEvtModel {
    ordem: TipoOrdenacaoEnum;
    coluna: string;
}
