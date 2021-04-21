import { StatusEnum } from '@shared/models/enum/status.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsContatoEmpresaModel extends ParamsConsultaDto {

  idEmpresa: number;
  nome: string;
  email: string;
  telefone: string;
  status: StatusEnum;
}
