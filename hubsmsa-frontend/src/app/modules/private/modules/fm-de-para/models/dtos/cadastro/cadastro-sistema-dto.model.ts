import { CampoSistemaDTO } from '../../campo-sistema-dto.model';

export interface CadastroSistemaDTO {
  idEmpresa: number;
  idSistema: number;
  camposDePara: Array<CampoSistemaDTO>;
}
