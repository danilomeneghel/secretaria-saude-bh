import { StatusEnum } from '@shared/models/enum/status.enum';
import { CodigoDescricao } from './codigoDescricao.models';

export interface QueryParamsDePara {
    idTipoDePara: number;
    nome: string;
    status: StatusEnum;
    idEmpresaPrimaria: number;
    idSistemaPrimario: number;
    idEmpresaSecundaria: number;
    idSistemaSecundario: number;
    listaDeParaPrimario: CodigoDescricao[];
    listaDeParaSecundario: CodigoDescricao[];
    ordem: string;
    pagina: number;
    coluna: string;
    itensPorPagina: number;
}
