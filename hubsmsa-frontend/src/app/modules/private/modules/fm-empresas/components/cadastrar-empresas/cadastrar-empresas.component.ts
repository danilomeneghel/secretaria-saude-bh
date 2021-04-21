import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AppUtilService } from '@core/services/app-util.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioValidators } from '@shared/modules/modules/validacao-formulario/model/funcoes-validacao.model';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';

const CAMPOS = {
  nomeEmpresarial: 'nomeEmpresarial',
  nomeFantasia: 'nomeFantasia',
  cnpj: 'cnpj',
  cnes: 'cnes',
  site: 'site',
  status: 'status'
}
const regexPattern = /^[0-9]{1,}$/;

@Component({
  selector: 'sbapp-cadastrar-empresas',
  templateUrl: './cadastrar-empresas.component.html',
  styleUrls: ['./cadastrar-empresas.component.css']
})
export class CadastrarEmpresasComponent extends FswCrudComponentModel implements OnInit {

  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;

  dataSourceStatusRadio: InputListaRadioModel[];
  formPrincipal: FormGroup;
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idEmpresa: number;
  constructor(
    private formBuilder: FormBuilder,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    empresaService: EmpresaService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService
  ) {
    super(router, activatedRoute, validacaoService, empresaService, ngcAlert, fabricaModalService);
    this.titulo = 'Cadastro de Empresa';
  }

  ngOnInit() {
    super.ngOnInit();
    this.recuperarId();

  }

  recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idEmpresa = param['id'];
      if (this.idEmpresa) {
        this.prencherFormulario();
        this.titulo = 'Edição de Empresa';
      }
    })
  }

  private prencherFormulario() {
    this.recuperarDados(this.idEmpresa);
  }

  criarFormulario() {
    this.formPrincipal = this.formBuilder.group({
      [CAMPOS.nomeEmpresarial]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.nomeFantasia]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.cnpj]: [null, [ValidacaoFormularioValidators.validacaoCpfCnpj]],
      [CAMPOS.cnes]: [null,[Validators.pattern(regexPattern)]],
      [CAMPOS.site]: [],
      [CAMPOS.status]: [ Validators.required]
    });
    this.dataSourceStatusRadio = AppUtilService.valoresPadraoInputRadioStatus();
  }

  mapearCadastroDto() {
    const form: Empresa = this.formPrincipal.getRawValue();
    for (const key in form) {
      if (form.hasOwnProperty(key) && typeof form[key] === 'string') {
        form[key] = form[key].trim();
      }
    }
    form.cnpj = KzmaskDirective.removerMascara(form.cnpj);

    return form;
  }

  mapearEdicaoDto() {
    const form = this.formPrincipal.getRawValue();
    return form;
  }

  salvarEmpresa() {
    this.salvarDados();
  }
  mapearDto(dados: Empresa) {
    return dados;
  }
}