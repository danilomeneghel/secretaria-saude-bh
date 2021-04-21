import { Component, EventEmitter, Input, OnInit, Output, PipeTransform, TemplateRef } from '@angular/core';
import { PageChangedEvent } from 'ngx-bootstrap';
import { PaginaResultadoPiweb } from '../../models/pagina-resultado-piweb.model';
import { PiwebTabelaConfig } from '../../models/piweb-tabela-config.model';
import { TabelaPiwebOrdemEvtModel } from '../../models/tabela-piweb-ordem.evt.model';
import { PiwebUtilService } from '../../services/piweb-utils.service';


/**
 * @author Sergio Davi <sergio.teotonio@ctis.com.br>
 */
@Component({
  selector: 'piweb-tabela-simples',
  templateUrl: './piweb-tabela-simples.component.html',
  styleUrls: ['./piweb-tabela-simples.component.css']
})
export class PiwebTabelaSimplesComponent implements OnInit {


  @Input() paginaResultado: PaginaResultadoPiweb<any>;

  @Input() tabelaConfig: PiwebTabelaConfig;

  @Input() numeroPaginaAtual: number;

  @Input() templates: Record<string, TemplateRef<any>>;

  @Output()
  ordenarColuna: EventEmitter<TabelaPiwebOrdemEvtModel> = new EventEmitter();

  @Output() consultarPagina: EventEmitter<number> = new EventEmitter();

  ultimaColuna: string;
  ultimaOrdem: string;

  mId: string;

  pipes: PipeTransform[];

  constructor(
    private piwebUtilsService: PiwebUtilService
  ) { }


  ngOnInit() {
    this.mId = this.piwebUtilsService.generateHashId();
    this.carregarConfigPadrao();
  }


  private carregarConfigPadrao(): any {
    if (this.tabelaConfig) {
      if (!this.tabelaConfig.atributoOrdenacao) {
        this.tabelaConfig.atributoOrdenacao = {
          ASC: 'ASC',
          DESC: 'DESC'
        };
      }
      if (!this.tabelaConfig.rotaEditar) {
        this.tabelaConfig.rotaEditar = '../editar/';
      }
      if (this.tabelaConfig.exibirBotoesEditar === undefined) {
        this.tabelaConfig.exibirBotoesEditar = true;
      }
      if (!this.tabelaConfig.nomeAtributoIdentificador) {
        this.tabelaConfig.nomeAtributoIdentificador = 'id';
      }
      if (this.tabelaConfig.colunasConfig) {
        const colunaDef = this.tabelaConfig.colunasConfig.find(x => x.default);
        this.ultimaColuna = colunaDef ? colunaDef.nomeAtributoModelo : null;
      }
    }
  }


  novaPagina(pagina: PageChangedEvent) {
    if (this.numeroPaginaAtual !== pagina.page) {
      this.consultarPagina.emit(pagina.page);
    }
  }

  ordenar(coluna: string): void {
    let ordem = this.tabelaConfig.atributoOrdenacao.ASC;
    if (this.ultimaColuna != coluna) {
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

}
