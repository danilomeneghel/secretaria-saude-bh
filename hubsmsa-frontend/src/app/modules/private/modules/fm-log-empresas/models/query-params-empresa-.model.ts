import { StatusEnum } from '@shared/models/enum/status.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsEmpresaModel extends ParamsConsultaDto {

  nomeEmpresarial: string;
  nomeFantasia: string;
  cnpj: number;
  cnes: number;
  status: StatusEnum;
}
