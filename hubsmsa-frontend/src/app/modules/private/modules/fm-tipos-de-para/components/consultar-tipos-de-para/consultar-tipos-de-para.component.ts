import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';

const CAMPOS = {
  nome: 'nome',
  status: 'status',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina'
}
@Component({
  selector: 'sbapp-consultar-tipos-de-para',
  templateUrl: './consultar-tipos-de-para.component.html',
  styleUrls: ['./consultar-tipos-de-para.component.css']
})
export class ConsultarTiposDeParaComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  opcoesCheckboxStatus: InputListaCheckboxModel[];

  resultadoPaginaAtual: PaginaResultadoPiweb<TiposDePara>;
  statusEnumMsg = statusEnumMensagem;
  permissaoEnum = PermissaoUsuarioEnum;

  constructor(
    private formBuilder: FormBuilder,
    protected tiposDeParaService: TiposDeParaService,
    validacaoService: ValidacaoFormularioService,
    ngcAlert: NgcAlertService,
    private fabricaModalService: FabricaModalService
  ) {
    super(validacaoService, tiposDeParaService, ngcAlert);
  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.nome]: [],
      [CAMPOS.status]: [],

      [CAMPOS.ordem]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    this.pesquisarDados(pagina);

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

    this.tiposDeParaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.tiposDeParaService.gerarExcel(queryParams);
  }

  excluirEmpresa(idTipoDePara: number){
    this.fabricaModalService.modalConfirmarExcluir(
      MensagemEnum.CONFIRMAR_EXCLUSAO_TIPO_DEPARA
    ).onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.tiposDeParaService.removerDados(idTipoDePara).subscribe(() => {this.pesquisarDados()});
      }
    });
  }

}
