import { Component, OnInit, OnDestroy } from '@angular/core';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { FormGroup, FormBuilder } from '@angular/forms';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { Observable, Subscription } from 'rxjs';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { ContatoEmpresaService } from '../../services/contato-empresa.service';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { ContatoEmpresa } from '../../models/contato-empresa.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';
import { SharedDataService } from 'app/shared/shared-services/data.service';
const CAMPOS = {
  idEmpresa: 'idEmpresa',
  nome: 'nome',
  email: 'email',
  telefone: 'telefone',
  status: 'status',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}
@Component({
  selector: 'sbapp-consultar-contato-empresas',
  templateUrl: './consultar-contato-empresas.component.html',
  styleUrls: ['./consultar-contato-empresas.component.css']
})
export class ConsultarContatoEmpresasComponent extends FswPesquisarComponentModel implements OnInit, OnDestroy, FswPesquisarModel {

  formConsultarContatoEmpresa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<ContatoEmpresa>;
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  currentEmpresaSubs: Subscription;
  idEmpresaSelecionada: any;

  constructor(
    private empresaService: EmpresaService,
    private formBuilder: FormBuilder,
    protected contatoEmpresaService: ContatoEmpresaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
    private fabricaModalService: FabricaModalService,
    private sharedDataServce: SharedDataService
  ) {
    super(validacaoService, contatoEmpresaService, ngcAlert);
  }

  ngOnInit() {
    this.ngcAlert.clearAll();
    this.criarFormulario();
    this.preencherEmpresas();
    this.resolveEmpresaSelecionada();
  }

  ngOnDestroy() {
    this.currentEmpresaSubs.unsubscribe();
  }

  private resolveEmpresaSelecionada() {
    this.currentEmpresaSubs = this.sharedDataServce
      .getSharedEmpresa()
      .subscribe(e => this.idEmpresaSelecionada = e);
    if (this.idEmpresaSelecionada) {
      this.formPesquisa.get('idEmpresa').setValue(this.idEmpresaSelecionada);
      this.pesquisarDados();
    }
  }

  preencherEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao();
  }

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [],
      [CAMPOS.nome]: [],
      [CAMPOS.email]: [],
      [CAMPOS.telefone]: [],

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

  mapearPesquisa(pagina?: PiwebPaginaEvt): ContatoEmpresa {
    const params = this.formPesquisa.getRawValue();
    this.adicionarPagina(params, pagina);
    params.telefone = KzmaskDirective.removerMascara(params.telefone);


    Object.seal(params);

    return params;
  }

  excluirContatoEmpresa(idContatoEmpresa: number) {
    this.fabricaModalService.modalConfirmarExcluirRegistro().onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.contatoEmpresaService.removerDados(idContatoEmpresa).subscribe(() => { this.pesquisarDados()});
      }
    });
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.contatoEmpresaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.contatoEmpresaService.gerarExcel(queryParams);
  }

}

