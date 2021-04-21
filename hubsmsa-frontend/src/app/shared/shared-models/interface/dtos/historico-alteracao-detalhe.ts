import { Alteracao } from './alteracao';

export interface HistoricoAlteracaoDetalhe {
    nome: string;
    email: string;
    login: string;
    dataEvento: string;
    revisao: number;
    alteracoes: Alteracao[];
}
