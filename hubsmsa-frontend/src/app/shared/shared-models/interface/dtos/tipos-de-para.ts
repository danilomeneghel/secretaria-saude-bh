import { StatusEnum } from '@shared/models/enum/status.enum';
import { FormaCadastroEnum } from '@shared/models/enum/forma-cadastro.enum';

export interface TiposDePara {
    id: number;
    nome: string;
    descricao: string;
    status: StatusEnum;
    formaCadastro: FormaCadastroEnum;
    dataAtualizacao: string;
}