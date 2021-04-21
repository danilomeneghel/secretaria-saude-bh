import { FormaCadastroEnum } from './../../../../../../shared/shared-models/enum/forma-cadastro.enum';
import { StatusEnum } from '@shared/models/enum/status.enum';

export interface PesquisarExemploDtoModel {
  id: number;
  codigo: number;
  nomeExemplo: string;
  formaCadastro: FormaCadastroEnum;
  dataAtualizacao: string;
  status: StatusEnum;

}
