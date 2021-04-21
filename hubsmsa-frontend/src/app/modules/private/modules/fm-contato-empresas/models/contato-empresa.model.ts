import { StatusEnum } from '@shared/models/enum/status.enum';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
export interface ContatoEmpresa {
    id: number;
    empresa: Empresa;
    nome: string;
    email: string;
    telefone: string;
    setor: string;
    status: StatusEnum;

}