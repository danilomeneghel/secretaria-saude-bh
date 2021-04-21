import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { LogContatoEmpresasService } from '../../services/log-contato-empresas.service';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { Observable } from 'rxjs';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { LogContatoEmpresa } from '@shared/models/interface/dtos/log-contato-empresa.model';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { ContatoEmpresa } from '../../../fm-contato-empresas/models/contato-empresa.model';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',
  nome: 'nome',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-log-contato-empresas',
  templateUrl: './consultar-log-contato-empresas.component.html',
  styleUrls: ['./consultar-log-contato-empresas.component.css']
})
export class ConsultarLogContatoEmpresasComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formPesquisa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<ContatoEmpresa>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  id: number;

  constructor(
    private formBuilder: FormBuilder,
    protected logContatoEmpresaService: LogContatoEmpresasService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService
  ) {
    super(validacaoService, logContatoEmpresaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.preencherEmpresas();
  }

  validarFormulario(){
    return !(this.formPesquisa.controls.idEmpresa.value == null &&
    this.formPesquisa.controls.dataInicial.value == null &&
    this.formPesquisa.controls.dataFinal.value == null &&
    this.formPesquisa.controls.nome.value == null);
  }

  validarPeriodoData() {
    return !(this.formPesquisa.controls.dataFinal.value != null &&
      this.formPesquisa.controls.dataInicial.value > this.formPesquisa.controls.dataFinal.value );
  }

  preencherEmpresas() {
    this.listaEmpresasObs = this.logContatoEmpresaService.recuperarSelecao();
  }

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [],
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],
      [CAMPOS.nome]: [],


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
      this.ngcAlert.clearAll();
      this.recuperarHistoricoAlteracao(_pagina);
    }

  }

  ordenar($event) {
    this.formPesquisa.patchValue($event);
    this.recuperarHistoricoAlteracao(this.paginaAtualCfg);
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logContatoEmpresaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logContatoEmpresaService.gerarExcel(queryParams);
  }

  mapearPesquisa(pagina?: PiwebPaginaEvt): LogContatoEmpresa {
    const params = this.formPesquisa.getRawValue();
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }

  recuperarHistoricoAlteracao(pagina?: PiwebPaginaEvt) {
    const paginaAtual = pagina || this.paginaAtualCfg;
    const queryParams = this.formPesquisa.getRawValue();
    const params = queryParams;
    params.pagina = paginaAtual.pagina;
    params.itensPorPagina = paginaAtual.itensPorPagina;
    this.resultadoTabelaHistoricoAlteracao = null;
    this.id = this.formPesquisa.controls.idEmpresa.value;
    this.logContatoEmpresaService.recuperarHistoricoAlteracao(queryParams).subscribe(
      historicoAlteracao => this.resultadoTabelaHistoricoAlteracao = historicoAlteracao
    );
  }
}
