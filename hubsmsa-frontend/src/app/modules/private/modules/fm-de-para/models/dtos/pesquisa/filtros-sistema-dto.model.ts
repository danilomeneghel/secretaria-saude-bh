
import { CampoSistemaDTO } from '../../campo-sistema-dto.model';

export interface FiltroSistemaDTO {
  idEmpresa: number;
  idSistema: number;
  campos: Array<CampoSistemaDTO>;
}
