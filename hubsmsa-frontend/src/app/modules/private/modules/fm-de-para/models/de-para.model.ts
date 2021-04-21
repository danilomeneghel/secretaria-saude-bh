import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';
import { StatusEnum } from '@shared/models/enum/status.enum';
import { CodigoDescricao } from './codigoDescricao.models';

export interface DePara {
    id: number,
    nomeDePara: string;
    nomeTipoDePara: string;
    tipoDePara: TiposDePara;
    sistemaPrimario: Sistema;
    sistemaSecundario: Sistema;
    nome: string;
    deParaPrimario: CodigoDescricao[];
    deParaSecundario: CodigoDescricao[];
    status: StatusEnum;
}
