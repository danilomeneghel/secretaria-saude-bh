import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Observable } from 'rxjs';
import { LogSistema } from '@shared/models/interface/dtos/log-sistema.model';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  empresa: 'empresa',
  nome: 'nome',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-sistemas',
  templateUrl: './consultar-log-sistemas.component.html',
  styleUrls: ['./consultar-log-sistemas.component.css']
})

export class ConsultarLogSistemasComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formConsultarSistema: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<LogSistema>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  id: number;
  formPesquisa: any;

  constructor(
    private formBuilder: FormBuilder,
    private empresaService: EmpresaService,
    protected sistemaService: SistemaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService
  ) {
    super(validacaoService, sistemaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.preencherEmpresas();
  }

  preencherEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao();
  }

  validarFormulario(){
    return !(this.formPesquisa.controls.idEmpresa.value == null &&
    this.formPesquisa.controls.nome.value == null &&
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
      [CAMPOS.nome]: [],
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],
      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    if (!this.validarFormulario()) {
      this.ngcAlert.error(MensagemEnum.NECESSARIO_PREENCHER_FILTRO);
    } else if (!this.validarPeriodoData()) {
      this.ngcAlert.error(MensagemEnum.FILTRO_DATA_FINAL_INFERIOR);
    } else {
      this.ngcAlert.clearAll();
      this.recuperarHistoricoAlteracao(_pagina);
    }
  }

  limpar() {
    this.ngcAlert.clearAll();
    this.formPesquisa.reset();
    resetInputListCheckbox(this.opcoesCheckboxStatus);

    this.resultadoPaginaAtual = null;
    this.paginaAtualCfg = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  }

  ordenar($event) {
    this.formPesquisa.patchValue($event);
    this.recuperarHistoricoAlteracao(this.paginaAtualCfg);
  }

  mapearPesquisa(pagina?: PiwebPaginaEvt): LogSistema {
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

    this.sistemaService.recuperarHistoricoAlteracao(queryParams).subscribe(historicoAlteracao => this.resultadoTabelaHistoricoAlteracao = historicoAlteracao);
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();

    this.sistemaService.gerarCsv(queryParams, '/log');
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();

    this.sistemaService.gerarExcel(queryParams, '/log');
  }

}
