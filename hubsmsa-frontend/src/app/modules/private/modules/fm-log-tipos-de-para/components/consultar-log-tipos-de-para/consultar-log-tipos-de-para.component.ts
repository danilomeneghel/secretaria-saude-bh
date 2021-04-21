import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { LogTiposDePara } from '@shared/models/interface/dtos/log-tipos-de-para';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { LogTiposDeParaService } from '../../services/log-tipos-de-para.service';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';


const CAMPOS = {
  nome: 'nome',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',
  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina'
}
@Component({
  selector: 'sbapp-log-consultar-tipos-de-para',
  templateUrl: './consultar-log-tipos-de-para.component.html',
  styleUrls: ['./consultar-log-tipos-de-para.component.css']
})
export class ConsultarLogTiposDeParaComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  opcoesCheckboxStatus: InputListaCheckboxModel[];
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  resultadoPaginaAtual: PaginaResultadoPiweb<LogTiposDePara>;
  statusEnumMsg = statusEnumMensagem;
  permissaoEnum = PermissaoUsuarioEnum;

  constructor(
    private formBuilder: FormBuilder,
    protected logTiposDeParaService: LogTiposDeParaService,
    validacaoService: ValidacaoFormularioService,
    ngcAlert: NgcAlertService,
  ) {
    super(validacaoService, logTiposDeParaService, ngcAlert);
  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.nome]: [],
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],
      [CAMPOS.ordem]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  validarFormulario() {
    return !(this.formPesquisa.controls.nome.value == null &&
    this.formPesquisa.controls.dataInicial.value == null &&
    this.formPesquisa.controls.dataFinal.value == null);
  }

  validarPeriodoData() {
    return !(this.formPesquisa.controls.dataFinal.value != null &&
      this.formPesquisa.controls.dataInicial.value > this.formPesquisa.controls.dataFinal.value );
  }


  btnPesquisar(pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    const params = this.mapearPesquisa(pagina);

    if (!this.validarFormulario()) {
      this.ngcAlert.error(MensagemEnum.NECESSARIO_PREENCHER_FILTRO);
    } else if (!this.validarPeriodoData()) {
      this.ngcAlert.error(MensagemEnum.FILTRO_DATA_FINAL_INFERIOR);
    } else {
      this.ngcAlert.clearAll();
      this.pesquisarDados(pagina, params);
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
    this.pesquisarDados();
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logTiposDeParaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logTiposDeParaService.gerarExcel(queryParams);
  }
}
