import { PiwebColunaModel } from './piweb-coluna-model';

export interface PiwebTabelaConfig {
    /**
     * Configuração de colunas
     */
    colunasConfig: PiwebColunaModel[];
    /**
     * Nome do atributo identificador do modelo utlizado para listagem na tabela.
     */
    nomeAtributoIdentificador?: string;
    /**
     * Titulo da tabela localizado na parte superior
     */
    tituloTabela?: string;
    /**
     * Rota para editar o elemento escolhido
     * @default '../editar/modelo[nomeAtributoIdentificador]
     */
    rotaEditar?: string;
    /**
     * Exibir os ícones para editar o elemento.
     * @default true
     */
    exibirBotoesEditar?: boolean;
    /**
     * Definir o atributo de ordenação que será enviado atraves do modelo de evento, ao selcionar uma ordenação por coluna na tabela.
        @default {ASC:'ASC',DESC:'DESC'}
     */
    atributoOrdenacao?: {
        ASC: string,
        DESC: string
    };
}
