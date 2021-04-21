import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Observable } from 'rxjs';
import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  nomeSistema: 'nomeSistema',
  status: 'status',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-sistemas',
  templateUrl: './consultar-sistemas.component.html',
  styleUrls: ['./consultar-sistemas.component.css']
})

export class ConsultarSistemasComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formConsultarSistema: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<Sistema>;
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  constructor(
    private empresaService: EmpresaService,
    private formBuilder: FormBuilder,
    protected sistemaService: SistemaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
    private fabricaModalService: FabricaModalService
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

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [],
      [CAMPOS.nomeSistema]: [],
      [CAMPOS.status]: [],
      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll()

    this.pesquisarDados(_pagina);
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

  mapearPesquisa(pagina?: PiwebPaginaEvt): Sistema {
    const params = this.formPesquisa.getRawValue();
    params.cnpj = KzmaskDirective.removerMascara(params.cnpj);
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }

  excluirSistema(idSistema: number) {
    this.fabricaModalService
    .modalConfirmarExcluir(MensagemEnum.CONFIRMAR_EXCLUSAO_SISTEMA)
    .onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.sistemaService.removerDados(idSistema).subscribe(() => { this.pesquisarDados()});
      }
    });
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();

    this.sistemaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();

    this.sistemaService.gerarExcel(queryParams);
  }

}
