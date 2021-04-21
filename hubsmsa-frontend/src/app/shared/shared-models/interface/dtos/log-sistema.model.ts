import { Empresa } from './empresa.model';

export interface LogSistema {
    dataEvento: Date;
    usuario: string;
    empresa: Empresa;
    nome: string;
    antes: string;
    depois: string;
    revisao: number;
}