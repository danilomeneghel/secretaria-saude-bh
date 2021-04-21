import { PaginaResultadoPiweb, PiwebPaginaEvt, PiwebTabelaCfg } from '@shared/modules/modules/piweb-tabela';

export class PaginacaoOrdenacaoFiltragemService<T> {

    itemSelecionadoId: number;

    itemSelecionado = {};

    paginaAtualCfg: PiwebPaginaEvt = { pagina: 1, itensPorPagina: 10 };

    resultadoPaginaAtualTemp: PaginaResultadoPiweb<T> = { itens: [], totalRegistros: 0 };

    ultimaFiltragem: [];

    constructor(
        paginaPadrao: number,
        itensPorPaginaPadrao: number) {

        this.paginaAtualCfg.pagina = paginaPadrao;
        this.paginaAtualCfg.itensPorPagina = itensPorPaginaPadrao;

    }

    getFiltros(event = null): Array<any> {

        const arrayIds = [];

        if (event) {
            this.ultimaFiltragem = event;
        }

        let filtros = (event) ? event : this.ultimaFiltragem;
        filtros = (!filtros) ? [] : filtros;

        for (let i = 0; i < filtros.length; i++) {
            arrayIds.push(filtros[i].data.id);
        }

        return arrayIds;

    }

    executarPaginacao(resultadoPaginaAtual: PaginaResultadoPiweb<T>, pagina?: any) {

        const ids = this.getFiltros();

        if (!pagina) {
            this.paginacaoPadrao(resultadoPaginaAtual);
        }

        if (pagina) {

            this.atualizarVarDaPag(pagina);
            resultadoPaginaAtual.itens = this.pegarDadosPorPagina(
                this.filtrarPorTipoEstrutura(ids)
            );
            resultadoPaginaAtual.totalRegistros = this.pegarTotalRegistro(ids);

        }

    }

    filtrarPorTipoEstrutura(arrayId) {

        if (arrayId.length === 0 || arrayId.length === 3) {
            return JSON.parse(JSON.stringify(this.resultadoPaginaAtualTemp.itens));
        }

        return this.resultadoPaginaAtualTemp.itens.filter(result => {

            for (const item of arrayId) {
                if (result['idTipoEstrutura'] === item) {
                    return true;
                }
            }

        });

    }

    ordenar(resultadoPaginaAtual: PaginaResultadoPiweb<T>, event: any) {

        if (event.coluna.toLowerCase().indexOf('codigo') !== -1 || event.coluna.toLowerCase().indexOf('id') !== -1) {
            this.ordenarNumero(this.resultadoPaginaAtualTemp.itens, event.ordem, event.coluna);
        } else {
            this.ordenarTexto(this.resultadoPaginaAtualTemp.itens, event.ordem, event.coluna);
        }

        this.executarPaginacao(resultadoPaginaAtual, this.paginaAtualCfg);

    }

    ordenarNumero(itens, tipo, colunaAtual) {

        itens.sort((a, b) => {

            const vA = +a[colunaAtual];

            const vB = +b[colunaAtual];

            if (tipo === 'ASC') {

                return vA < vB ? -1 : vA > vB ? 1 : 0;

            } else {

                return vA > vB ? -1 : vA < vB ? 1 : 0;

            }

        });

    }

    ordenarTexto(itens, tipo, colunaAtual) {

        itens.sort((a, b) => {

            if (!a[colunaAtual]) {
                a[colunaAtual] = '';
            }

            if (!b[colunaAtual]) {
                b[colunaAtual] = '';
            }

            if (tipo === 'ASC') {

                return a[colunaAtual].toLowerCase() < b[colunaAtual].toLowerCase() ? -1 :
                    a[colunaAtual].toLowerCase() > b[colunaAtual].toLowerCase() ? 1 : 0;

            } else {

                return a[colunaAtual].toLowerCase() > b[colunaAtual].toLowerCase() ? -1 :
                    a[colunaAtual].toLowerCase() < b[colunaAtual].toLowerCase() ? 1 : 0;

            }

        });

    }

    aplicarFiltragem(resultadoPaginaAtual: PaginaResultadoPiweb<T>, tabelaCfg: PiwebTabelaCfg, event = null) {

        const ids = this.getFiltros(event);

        if (ids.length === 0 || ids.length === 3) {

            resultadoPaginaAtual.itens = this.pegarDadosPorPagina();
            resultadoPaginaAtual.totalRegistros = this.resultadoPaginaAtualTemp.totalRegistros;

        } else {

            resultadoPaginaAtual.itens = this.pegarDadosPorPagina(
                this.filtrarPorTipoEstrutura(ids)
            );
            resultadoPaginaAtual.totalRegistros = this.pegarTotalRegistro(ids);

        }

        this.ocultarPaginacao(resultadoPaginaAtual, tabelaCfg);

    }

    ocultarPaginacao(resultadoPaginaAtual: PaginaResultadoPiweb<T>, tabelaCfg: PiwebTabelaCfg) {

        if (resultadoPaginaAtual.itens.length === 0) {
            tabelaCfg.esconderPaginacao = true;
        } else {
            tabelaCfg.esconderPaginacao = false;
        }

    }

    removarItemArray(id, array: PaginaResultadoPiweb<any>) {

        for (const i in array.itens) {

            if (array.itens[i].id === id) {
                array.itens.splice(+i, 1);
                break;
            }

        }

    }

    pegarDadosPorPagina(items?) {

        return JSON.parse(JSON.stringify((items) ? items : this.resultadoPaginaAtualTemp.itens))
            .splice(((this.paginaAtualCfg.pagina - 1) * this.paginaAtualCfg.itensPorPagina),
                this.paginaAtualCfg.itensPorPagina);

    }

    paginacaoPadrao(resultadoPaginaAtual: PaginaResultadoPiweb<T>) {

        const arrayRef = JSON.parse(JSON.stringify(this.resultadoPaginaAtualTemp.itens));

        const totalItensAserExibido = (this.paginaAtualCfg.pagina * this.paginaAtualCfg.itensPorPagina);

        if (arrayRef.length > totalItensAserExibido) {
            resultadoPaginaAtual.itens = arrayRef.splice(0, totalItensAserExibido);
        } else {
            resultadoPaginaAtual.itens = arrayRef;
        }

        resultadoPaginaAtual.totalRegistros = this.resultadoPaginaAtualTemp.totalRegistros;

    }

    atualizarVarDaPag(pagina) {

        this.paginaAtualCfg.itensPorPagina = pagina.itensPorPagina;
        this.paginaAtualCfg.pagina = pagina.pagina;

    }

    pegarTotalRegistro(arrayId) {

        if (arrayId.length === 0 || arrayId.length === 3) {
            return this.resultadoPaginaAtualTemp.itens.length;
        }

        return this.resultadoPaginaAtualTemp.itens.filter(result => {

            for (const item of arrayId) {
                if (result['idTipoEstrutura'] === item) {
                    return true;
                }
            }

        }).length;

    }

    copiarDados(resultadoPaginaAtual: PaginaResultadoPiweb<T>): void {

        resultadoPaginaAtual.itens = this.pegarDadosPorPagina();
        resultadoPaginaAtual.totalRegistros = this.resultadoPaginaAtualTemp.totalRegistros;

    }

    filtrarId(array, id, identificador) {
        return array.filter(estrutura => {
            return (estrutura[identificador] === id) ? true : false;
        });
    }

    removerItemArrayEstrutura(arrayEstrutura, id) {
        if (id) {
            for (let i in arrayEstrutura) {
                if (arrayEstrutura[i].id === id) {
                    arrayEstrutura.splice(i,1);
                    break;
                }
            }
        }
    }

    inserirIdentificadores(arrayEstrutura, array, estruturaId?) {

        arrayEstrutura = JSON.parse( JSON.stringify( arrayEstrutura ) );

        for (const item of array.itens) {

            if (item.id < 0 || !item.id) {

                const objTemp = this.filtrarId(arrayEstrutura,
                    (item.idEstrutura ? item.idEstrutura : item.idNivelEstrutural),
                    'idEstrutural');

                let encontrou = false;

                if (estruturaId && item.id === estruturaId) {
                    this.itemSelecionadoId = objTemp[0].id;
                    encontrou = true;
                }

                item.id = objTemp[0].id;
                delete item.itemEditado;

                this.removerItemArrayEstrutura(arrayEstrutura, objTemp[0].id);                

                if (encontrou) {
                    this.itemSelecionado = item;
                }

            } else if (estruturaId && estruturaId > 0) {

                if (estruturaId && item.id === estruturaId) {
                    this.itemSelecionado = item;
                }

            }

        }

    }

    inserirInicioPosPaginaAtual(item: any): void {

        this.resultadoPaginaAtualTemp.itens.splice(
            ((this.paginaAtualCfg.pagina * this.paginaAtualCfg.itensPorPagina) - this.paginaAtualCfg.itensPorPagina),
            0,
            item
        );

        this.resultadoPaginaAtualTemp.totalRegistros = this.resultadoPaginaAtualTemp.itens.length;

    }

    possuiFilhos(estruturaId): Array<T> {

        return this.resultadoPaginaAtualTemp.itens.filter(item => {
            return (item['id'] === estruturaId && item['possuiFilhos']) ? true : false;
        });

    }

    get getItensPorPaginaPadrao() {
        return this.paginaAtualCfg.itensPorPagina;
    }

    get getItemSelecionadoId() {
        return this.itemSelecionadoId;
    }

    get getItemSelecionado() {
        return this.itemSelecionado;
    }

    get getPaginaAtualCfg() {
        return this.paginaAtualCfg;
    }

    get getPaginaAtual() {
        return this.paginaAtualCfg.pagina;
    }

    get getItensPorPagina() {
        return this.paginaAtualCfg.itensPorPagina;
    }

    get getResultadoPaginaAtualTemp() {
        return this.resultadoPaginaAtualTemp;
    }

    setResultadoPaginaAtualTemp(resultadoPaginaAtual: PaginaResultadoPiweb<T>): void {
        this.resultadoPaginaAtualTemp = resultadoPaginaAtual;
    }

    decrementarPaginaAtual(): void {
        this.paginaAtualCfg.pagina--;
    }

    decrementarTotalRegistro(): void {
        this.resultadoPaginaAtualTemp.totalRegistros--;
    }

}
