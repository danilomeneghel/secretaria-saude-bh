import { CadastroSistemaDTO } from './cadastro-sistema-dto.model';

export class CadastroDeParaDTO  {
  idTipoDePara: number;
  nomeDePara: string;
  status: string;
  sistemaPrimario: CadastroSistemaDTO;
  sistemaSecundario: CadastroSistemaDTO;
}
