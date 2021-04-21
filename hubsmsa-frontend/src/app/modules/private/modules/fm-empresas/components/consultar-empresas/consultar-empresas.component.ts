import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Router } from '@angular/router';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { SharedDataService } from 'app/shared/shared-services/data.service';


const CAMPOS = {
  nomeEmpresarial: 'nomeEmpresarial',
  nomeFantasia: 'nomeFantasia',
  cnpj: 'cnpj',
  cnes: 'cnes',
  status: 'status',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-empresas',
  templateUrl: './consultar-empresas.component.html',
  styleUrls: ['./consultar-empresas.component.css']
})
export class ConsultarEmpresasComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {


  formConsultarEmpresa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<Empresa>;
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };

  constructor(
    private formBuilder: FormBuilder,
    protected empresaService: EmpresaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
    private fabricaModalService: FabricaModalService,
    private router: Router,
    private sharedDataServce: SharedDataService
  ) {
    super(validacaoService, empresaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
  }

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.nomeEmpresarial]: [],
      [CAMPOS.nomeFantasia]: [],
      [CAMPOS.cnpj]: [],
      [CAMPOS.cnes]: [],
      [CAMPOS.status]: [],

      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();

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

  mapearPesquisa(pagina?: PiwebPaginaEvt): Empresa {
    const params = this.formPesquisa.getRawValue();
    params.cnpj = KzmaskDirective.removerMascara(params.cnpj);
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }

  excluirEmpresa(idEmpresa: number) {
    this.fabricaModalService.modalConfirmarExcluirRegistro().onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.empresaService.removerDados(idEmpresa).subscribe(() => { this.pesquisarDados() });
      }
    });
  }

  redirecionarTelaDeContatoDeEmpresa(idEmpresa: number) {
    this.sharedDataServce.setSharedEmpresa(idEmpresa);
    this.router.navigateByUrl('/admin/contato-empresas');

  }

  validarTamanho() {
    let cnpj: String = this.formPesquisa.controls.cnpj.value
    if (cnpj != null && cnpj.length < 18) this.formPesquisa.controls.cnpj.reset()
  }
  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.empresaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.empresaService.gerarExcel(queryParams);
  }
}
