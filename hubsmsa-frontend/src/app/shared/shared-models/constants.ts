import { NotificacaoEnum, notificacaoEnumMensagem } from './enum/notificacao.enum';
import { INgxSelectOption } from 'ngx-select-ex';
import { statusEnumMensagem, StatusEnum } from './enum/status.enum';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { StatusExecucaoEnum, statusExecucaoEnumMensagem } from './enum/status-excecucao.enum';

export const dataSourceCheckboxStatus: InputListaCheckboxModel[] = [
  { id: 1, valorExibicao: statusEnumMensagem[StatusEnum.ATIVO], valor: StatusEnum.ATIVO, checked: false },
  { id: 2, valorExibicao: statusEnumMensagem[StatusEnum.INATIVO], valor: StatusEnum.INATIVO, checked: false }
];

export const dataSourceCheckboxNotificacao: InputListaCheckboxModel[] = [
  { id: 1, valorExibicao: notificacaoEnumMensagem[NotificacaoEnum.SUCESSO], valor: NotificacaoEnum.SUCESSO, checked: false },
  { id: 2, valorExibicao: notificacaoEnumMensagem[NotificacaoEnum.FALHA], valor: NotificacaoEnum.FALHA, checked: false }
];

export const dataSourceCheckboxStatusExecucao: InputListaCheckboxModel[] = [
  { id: 1, valorExibicao: statusExecucaoEnumMensagem[StatusExecucaoEnum.SUCESSO], valor: StatusExecucaoEnum.SUCESSO, checked: false },
  { id: 2, valorExibicao: statusExecucaoEnumMensagem[StatusExecucaoEnum.FALHA], valor: StatusExecucaoEnum.FALHA, checked: false }
];

export const dataSourceEstruturaRadio = [
  {
    valor: 'M',
    valorExibicao: 'Móvel',
    titleAttr: 'Estrutura itinerante.'
  },
  {
    valor: 'I',
    valorExibicao: 'Imóvel',
    titleAttr: 'Estrutura fixa.'
  }
];

export const valoresPadraoEstabelecimento = {
  statusEstabelecimento: 'A',
  endereco: {
    uf: 'MG',
    municipio: 'Belo Horizonte',
    codigoIbge: '3106200',
  }
};

export const dataSourceInternetRadio = [
  {
    valor: 'A',
    valorExibicao: 'Sim',
  },
  {
    valor: 'I',
    valorExibicao: 'Não',
  }
];

export const ngxSelectSearchCallback = (search: string, item: INgxSelectOption) => {
  const textNorm = item.text.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
  const searchNorm = search.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();
  return textNorm.indexOf(searchNorm) > -1 || (!search && item.text);
};
