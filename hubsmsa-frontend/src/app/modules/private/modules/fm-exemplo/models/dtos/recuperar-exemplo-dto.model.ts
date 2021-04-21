import { FormaCadastroEnum } from '@shared/models/enum/forma-cadastro.enum';
import { StatusEnum } from '@shared/models/enum/status.enum';

export interface RecuperarExemploDtoModel {
  codigo: number;
  nomeExemplo: string;
  status: StatusEnum;
  formaCadastro: FormaCadastroEnum;
}
