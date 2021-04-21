import { StatusEnum } from '@shared/models/enum/status.enum';
import { Sistema } from './sistema.model';

export interface Servico {
    id: number;
    sistemaPrimario: Sistema;
    sistemaSecundario: Sistema;
    nome: string;
    descricao: string;
    status: StatusEnum;
}
