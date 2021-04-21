import { StatusEnum } from '@shared/models/enum/status.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsSistemaModel extends ParamsConsultaDto {

  idEmpresa: number;
  empresa: string;
  sistema: string;
  status: StatusEnum;
}
