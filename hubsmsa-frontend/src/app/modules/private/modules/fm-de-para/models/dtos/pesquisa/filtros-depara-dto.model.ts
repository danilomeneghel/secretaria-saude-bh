import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { FiltroSistemaDTO } from './filtros-sistema-dto.model';

export class FiltrosDeParaDTO implements ParamsConsultaDto {
  idTipoDePara?: number;
  nomeDePara?: string;
  status?: string[];
  sistemaPrimario?: FiltroSistemaDTO;
  sistemaSecundario?: FiltroSistemaDTO;
  ordem?: string;
  pagina?: number;
  coluna?: string;
  itensPorPagina?: number;
}
