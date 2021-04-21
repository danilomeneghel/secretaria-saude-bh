import { OnDestroy, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { FswServicoHttpPadraoModel } from '@shared/models/interface/fsw-crud/fsw-servico-http-padrao.model';
import { PaginaResultadoPiweb, PiwebPaginaEvt, TabelaPiwebOrdemEvtModel } from '@shared/modules/modules/piweb-tabela';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';

export abstract class FswPesquisarComponentModel implements OnInit, FswPesquisarModel, OnDestroy {
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  abstract resultadoPaginaAtual: PaginaResultadoPiweb<any>;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };

  readonly modoTela = ModoTelaEnum.CONSULTA;

  private _titulo: string;
  formPesquisa: FormGroup;

  subscriptions: Subscription[] = [];

  protected constructor(
    protected validacaoService: ValidacaoFormularioService,
    protected servicePrincipal: Partial<FswServicoHttpPadraoModel>,
    protected ngcAlert: NgcAlertService
  ) {
  }

  ngOnInit(): void {
    this.criarFormulario();
    this.recuperarFiltroPesquisa();
  }

  abstract criarFormulario(): void;

  recuperarFiltroPesquisa() {
    const tempFiltroPesquisa = this.servicePrincipal.tempFiltroPesquisa;
    if (tempFiltroPesquisa) {
      this.formPesquisa.patchValue(tempFiltroPesquisa);
    }
  }

  abstract btnPesquisar(pagina?: PiwebPaginaEvt): void;

  pesquisarDados(pagina?: PiwebPaginaEvt, queryParams?: ParamsConsultaDto) {
    this.resultadoPaginaAtual = null;
    queryParams = queryParams || this.mapearPesquisa(pagina);

    this.servicePrincipal.tempFiltroPesquisa = this.formPesquisa.getRawValue();
    Object.seal(this.servicePrincipal.tempFiltroPesquisa);

    try {
      this.validacaoService.validarFormulario(this.formPesquisa);
      this.servicePrincipal.pesquisarDados(queryParams).subscribe(response => this.resultadoPaginaAtual = response);
      this.manterFiltros();
    } catch (error) {
      this.ngcAlert.error(error.message);
    }
  }

  private manterFiltros() {
    if (!this.servicePrincipal.tempFiltroPesquisa) {
      this.servicePrincipal.tempFiltroPesquisa = {} as FormGroup;
    }
  }

  limpar(): void {
    this.ngcAlert.clearAll();
    this.formPesquisa.reset();
    this.resultadoPaginaAtual = null;
    this.paginaAtualCfg = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  }

  ordenar(ordem: TabelaPiwebOrdemEvtModel) {
    this.formPesquisa.patchValue(ordem);
    this.btnPesquisar(this.paginaAtualCfg);
  }

  mapearPesquisa<T extends ParamsConsultaDto>(pagina?: PiwebPaginaEvt): T | any {
    const params: T = this.formPesquisa.getRawValue();
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }

  adicionarPagina<T extends ParamsConsultaDto>(params: T, pagina: PiwebPaginaEvt) {
    const paginaAtual = pagina || this.paginaAtualCfg;
    params.pagina = paginaAtual.pagina;
    params.itensPorPagina = paginaAtual.itensPorPagina;
    return params;
  }

  get titulo(): string {
    return this.modoTela + ' ' + this._titulo;
  }

  set titulo(tituloCsu: string) {
    this._titulo = tituloCsu;
  }

  ngOnDestroy(): void {
    if (this.subscriptions && this.subscriptions.length > 0) {
      this.subscriptions.forEach(subscription => subscription.unsubscribe());
    }
  }
}
