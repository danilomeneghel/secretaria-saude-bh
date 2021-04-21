import { StatusEnum } from '@shared/models/enum/status.enum';
import { Empresa } from './empresa.model';

export interface DePara {
    id: number;
    empresa: Empresa;
    nome: string;
    descricao: string;
    status: StatusEnum;
}