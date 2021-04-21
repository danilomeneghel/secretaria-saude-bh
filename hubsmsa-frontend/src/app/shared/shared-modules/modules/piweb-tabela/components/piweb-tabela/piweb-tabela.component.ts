// tslint:disable-next-line: max-line-length
import {
  AfterViewInit,
  Component,
  ContentChildren,
  DoCheck,
  EventEmitter,
  Input,
  IterableDiffers,
  OnDestroy,
  OnInit,
  Output,
  QueryList,
  ViewEncapsulation
} from '@angular/core';
import { FormControl } from '@angular/forms';
import { PageChangedEvent } from 'ngx-bootstrap';
import { Subscription } from 'rxjs';
import { PiwebColunaDirective } from '../../directives/piweb-coluna.directive';
import { PaginaResultadoPiweb, PiwebPaginaEvt, PiwebTabelaCfg, TabelaPiwebOrdemEvtModel } from '../../models';
import { PiwebUtilService } from '../../services/piweb-utils.service';


@Component({
  selector: 'piweb-tabela',
  templateUrl: './piweb-tabela.component.html',
  styleUrls: ['./piweb-tabela.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PiwebTabelaComponent implements OnInit, AfterViewInit, OnDestroy, DoCheck {
  iterableDiffer;

  constructor(
    private piwebUtilsService: PiwebUtilService,
    _iterableDiffers: IterableDiffers
  ) {
    this.iterableDiffer = _iterableDiffers.find([]).create(null);
  }

  @Input() paginaResultado: PaginaResultadoPiweb<any>;

  @ContentChildren(PiwebColunaDirective) componentColunas: QueryList<PiwebColunaDirective>;

  @Output()
  ordenarColuna: EventEmitter<TabelaPiwebOrdemEvtModel> = new EventEmitter();

  @Input() numeroPaginaAtual: number;

  @Input() multiSelecao: boolean;

  @Input() exibirLimiteLinhas: string;

  @Input() gerarCsv: () => void;

  @Input() gerarExcel: () => void; ;

  @Input() exibirBotoesExportarDados: boolean = false;

  @Input() linhasPorPagina: number[] = [3, 10];

  /**
   * @deprecated Utlizar o evento (novaPaginaEvt)
   */
  @Output() consultarPagina: EventEmitter<number> = new EventEmitter();

  @Output() novaPaginaEvt: EventEmitter<PiwebPaginaEvt> = new EventEmitter();

  @Output() itensSelecionadosEvt: EventEmitter<any[]> = new EventEmitter();

  @Input() tabelaConfig: PiwebTabelaCfg;

  @Input() collapseTitle = 'Resultado da pesquisa';

  private listaItensSelecionados: any[] = [];
  listaItens: TabelModel[];

  mId: string;
  ultimaColuna: string;
  ultimaOrdem: string;
  exibirTabelaBody = true;
  listaColunas: PiwebColunaDirective[] = [];

  selecionarItensPorPagina: FormControl = new FormControl();

  private subs: Subscription[];
  private quantItensPorPagina: number;

  static adicionarIdLinha(paginaResultado: PaginaResultadoPiweb<any>) {
    for (let i = 0; i < paginaResultado.itens.length; i++) {
      paginaResultado.itens[i].idLinha = i;
    }
  }

  ngOnInit() {
    this.mId = this.piwebUtilsService.generateHashId();
    this.carregarConfigPadrao();
    this.mapearListItens();
  }

  private cadastrarEventos() {
    this.subs = [
      this.selecionarItensPorPagina.valueChanges.subscribe(valor => this.solicitarNovaPagina(valor))
    ];
  }

  ngOnDestroy(): void {
    if (this.subs) {
      this.subs.forEach(sub => sub.unsubscribe());
    }
  }

  mapearListItens() {
    try {
      if (this.paginaResultado) {
        this.listaItens = this.paginaResultado.itens.map(modelo => {
          return {
            selecionado: false,
            modelo
          };
        });
        // this.filtrarColunas();
      }
    } catch (error) {
      console.error('Nao foi possivel mapear a lista de itens para a tabela', error);
    }
  }



  filtrarColunas() {
    if (this.paginaResultado
      && this.paginaResultado.itens
      && this.componentColunas
      && this.paginaResultado.itens.length > 0) {
      const elementoAtributos = Object.getOwnPropertyNames(this.paginaResultado.itens[0]);
      this.listaColunas = this.componentColunas.filter(coluna => {
        if (coluna.atributo) {
          return elementoAtributos.includes(coluna.atributo);
        }
        return true;
      });

    }
  }

  ngAfterViewInit(): void {
    if (this.componentColunas && this.componentColunas.length > 0) {
      this.ultimaColuna = this.componentColunas.first.atributo || '';
      this.componentColunas.forEach(coluna => {
        this.listaColunas.push(coluna);
      });
      setTimeout(() => {
        // this.filtrarColunas();
      });
    }
    if (this.linhasPorPagina) {
      setTimeout(() => {
        this.selecionarItensPorPagina.setValue(this.linhasPorPagina[0]);
        this.cadastrarEventos();
      });
    }
  }

  private carregarConfigPadrao(): any {
    if (this.tabelaConfig) {
      if (!this.tabelaConfig.atributoOrdenacao) {
        this.tabelaConfig.atributoOrdenacao = {
          ASC: 'ASC',
          DESC: 'DESC'
        };
      }
    } else {
      this.tabelaConfig = {
        atributoOrdenacao: {
          ASC: 'ASC',
          DESC: 'DESC'
        }
      };
    }
  }

  solicitarNovaPagina(intensByPaginaSelecionado: number) {
    this.quantItensPorPagina = intensByPaginaSelecionado;
    this.novaPaginaEvt.emit({
      itensPorPagina: Number(intensByPaginaSelecionado),
      pagina: 1
    });
  }

  exibir(tableClasslist: any) {
    this.exibirTabelaBody = !tableClasslist.contains('show');
  }

  novaPagina(pagina: PageChangedEvent) {
    if (this.numeroPaginaAtual !== pagina.page) {
      if (this.exibirLimiteLinhas) {
        this.novaPaginaEvt.emit({
          itensPorPagina: Number(this.quantItensPorPagina || this.selecionarItensPorPagina.value),
          pagina: pagina.page
        });
      } else {
        // tslint:disable-next-line: deprecation
        this.consultarPagina.emit(pagina.page);
      }
    }
  }

  ordenar(coluna: string): void {
    let ordem = this.tabelaConfig.atributoOrdenacao.ASC;
    if (this.ultimaColuna !== coluna) {
      this.ultimaColuna = coluna;
      ordem = this.tabelaConfig.atributoOrdenacao.ASC;
    } else {
      if (this.ultimaOrdem === this.tabelaConfig.atributoOrdenacao.ASC) {
        ordem = this.tabelaConfig.atributoOrdenacao.DESC;
      }
    }
    this.ultimaOrdem = ordem;
    this.ordenarColuna.emit({
      coluna: this.ultimaColuna,
      ordem
    });
  }

  selecionarTodos(selecionarTodosVar) {
    this.listaItens.forEach(item => {
      if (selecionarTodosVar) {
        if (!item.selecionado) {
          this.adicionarItemNaLista(item);
        }
      } else {
        this.removerItemNaLista(item);
      }
    });
    this.emitirEventoSelecionados();
  }

  selecionarItem(imputSelecionado, tabelaModel: TabelModel) {
    if (imputSelecionado) {
      // Adicionar
      this.adicionarItemNaLista(tabelaModel);
    } else {
      // Remover
      this.removerItemNaLista(tabelaModel);
    }
    this.emitirEventoSelecionados();
  }

  adicionarItemNaLista(tabelaModel: TabelModel) {
    if (!this.listaItensSelecionados.includes(tabelaModel.modelo)) {
      this.listaItensSelecionados.push(tabelaModel.modelo);
      tabelaModel.selecionado = true;
    }
  }

  emitirEventoSelecionados() {
    if (this.paginaResultado && this.paginaResultado.itens) {
      this.itensSelecionadosEvt.emit(this.listaItensSelecionados);
    }
  }

  removerItemNaLista(tabelaModel: TabelModel) {
    if (this.listaItensSelecionados.includes(tabelaModel.modelo)) {
      this.listaItensSelecionados.splice(
        this.listaItensSelecionados.findIndex(x => x === tabelaModel.modelo)
        , 1);
      tabelaModel.selecionado = false;
    }
  }

  ngDoCheck(): void {
    if (this.paginaResultado) {
      const changes = this.iterableDiffer.diff(this.paginaResultado.itens);
      if (changes) {
        this.mapearListItens();
      }
    }
  }

}


interface TabelModel {
  selecionado: boolean;
  modelo: any;
}
