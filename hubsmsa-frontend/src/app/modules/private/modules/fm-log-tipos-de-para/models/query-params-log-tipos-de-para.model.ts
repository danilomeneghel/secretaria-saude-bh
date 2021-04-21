import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsLogTiposDePara extends ParamsConsultaDto {
    nome: string;
    dataInicial: string;
    dataFinal: string;
}
  