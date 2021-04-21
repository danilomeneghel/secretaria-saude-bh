import { StatusEnum } from '@shared/models/enum/status.enum';

export interface Empresa {
    id: number;
    nomeEmpresarial: string;
    nomeFantasia: string;
    cnpj: string;
    cnes: number;
    status: StatusEnum;
    site: string;
}