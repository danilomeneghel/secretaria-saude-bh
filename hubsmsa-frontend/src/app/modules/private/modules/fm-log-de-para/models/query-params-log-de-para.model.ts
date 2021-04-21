import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';

export interface QueryParamsLogDePara extends ParamsConsultaDto {
    nome: string;
    dataInicial: string;
    dataFinal: string;
}
  