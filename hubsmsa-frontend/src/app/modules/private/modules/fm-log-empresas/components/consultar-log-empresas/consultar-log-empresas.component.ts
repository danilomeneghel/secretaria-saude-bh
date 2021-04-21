import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { Observable } from 'rxjs';
import { LogEmpresa } from '@shared/models/interface/dtos/log-empresa.model';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-empresas',
  templateUrl: './consultar-log-empresas.component.html',
  styleUrls: ['./consultar-log-empresas.component.css']
})
export class ConsultarLogEmpresasComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formConsultarEmpresa: FormGroup;
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<Empresa>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  id: number;

  constructor(
    private formBuilder: FormBuilder,
    protected logEmpresaService: EmpresaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService
  ) {
    super(validacaoService, logEmpresaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.preencherEmpresas();
  }

  preencherEmpresas() {
    this.listaEmpresasObs = this.logEmpresaService.recuperarSelecao();
  }

  validarFormulario() {
    return !(this.formPesquisa.controls.idEmpresa.value == null &&
    this.formPesquisa.controls.dataInicial.value == null &&
    this.formPesquisa.controls.dataFinal.value == null);
  }

  validarPeriodoData() {
    return !(this.formPesquisa.controls.dataFinal.value != null &&
      this.formPesquisa.controls.dataInicial.value > this.formPesquisa.controls.dataFinal.value );
  }

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [],
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],

      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    if (!this.validarFormulario()) {
      this.ngcAlert.error(MensagemEnum.NECESSARIO_PREENCHER_FILTRO);
    } else if (!this.validarPeriodoData()) {
      this.ngcAlert.error(MensagemEnum.FILTRO_DATA_FINAL_INFERIOR);
    } else {
      this.recuperarHistoricoAlteracao(_pagina);
    }
  }

  ordenar($event) {
    this.formPesquisa.patchValue($event);
    this.recuperarHistoricoAlteracao(this.paginaAtualCfg);
  }

  mapearPesquisa(pagina?: PiwebPaginaEvt): LogEmpresa {
    const params = this.formPesquisa.getRawValue();
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }


  recuperarHistoricoAlteracao(pagina?: PiwebPaginaEvt) {
    const paginaAtual = pagina || this.paginaAtualCfg;
    const queryParams = this.formPesquisa.getRawValue();
    let params = queryParams;
    params.pagina = paginaAtual.pagina;
    params.itensPorPagina = paginaAtual.itensPorPagina;

    this.resultadoTabelaHistoricoAlteracao = null;
    this.id = this.formPesquisa.controls.idEmpresa.value;

    this.logEmpresaService.recuperarHistoricoAlteracao(queryParams).subscribe(
        historicoAlteracao => this.resultadoTabelaHistoricoAlteracao = historicoAlteracao
      );
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logEmpresaService.gerarCsv(queryParams, '/log');
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logEmpresaService.gerarExcel(queryParams, '/log');
  }

}
