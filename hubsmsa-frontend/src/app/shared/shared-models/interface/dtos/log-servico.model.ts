import { StatusExecucaoEnum } from '@shared/models/enum/status-excecucao.enum';
import { Servico } from './servico.model';

export interface LogServico {
    id: number;
    servico: Servico;
    dataInicioEvento: string;
    dataFimEvento: string;
    statusExecucao: StatusExecucaoEnum;
}
