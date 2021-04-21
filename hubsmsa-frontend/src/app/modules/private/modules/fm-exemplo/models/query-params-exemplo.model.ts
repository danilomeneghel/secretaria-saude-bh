import { StatusEnum } from '@shared/models/enum/status.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsExemploModel extends ParamsConsultaDto {
  codigo: string;
  nomeExemplo: string;
  status: StatusEnum;
}
