import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AppUtilService } from '@core/services/app-util.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
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
import { Observable } from 'rxjs';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
const CAMPOS = {
  idEmpresa: 'idEmpresa',
  nome: 'nome',
  descricao: 'descricao',
  status: 'status'
}
@Component({
  selector: 'sbapp-cadastrar-sistemas',
  templateUrl: './cadastrar-sistemas.component.html',
  styleUrls: ['./cadastrar-sistemas.component.css']
})
export class CadastrarSistemasComponent extends FswCrudComponentModel implements OnInit {

  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  dataSourceStatusRadio: InputListaRadioModel[];
  formPrincipal: FormGroup;
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idSistema: number;
  constructor(
    private formBuilder: FormBuilder,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    sistemaService: SistemaService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService,
    private empresaService: EmpresaService
  ) {
    super(router, activatedRoute, validacaoService, sistemaService, ngcAlert, fabricaModalService);
    this.titulo = 'Cadastro de Sistema';
  }

  ngOnInit() {
    super.ngOnInit();
    this.recuperarId();
    this.preencherEmpresas();
  }

  recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idSistema = param['id'];
      if (this.idSistema) {
        this.prencherFormulario();
        this.titulo = 'Edição de Sistema';
      }
    })
  }
  preencherEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao();
  }
  mapearDto(dados: Sistema) {
    return {
      idEmpresa: dados.empresa.id,
      id: dados.id,
      nome: dados.nome,
      descricao: dados.descricao,
      status: dados.status
    }
  }

  private prencherFormulario() {
    this.recuperarDados(this.idSistema);
  }

  criarFormulario() {
    this.formPrincipal = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.nome]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.descricao]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.status]: [ Validators.required]
    });
    this.dataSourceStatusRadio = AppUtilService.valoresPadraoInputRadioStatus();
  }

  mapearCadastroDto() {
    const form: Sistema = this.formPrincipal.getRawValue();
    for (const key in form) {
      if (form.hasOwnProperty(key) && typeof form[key] === 'string') {
        form[key] = form[key].trim();
      }
    }

    return form;
  }
  compareFn(a, b) {

    return a.id === b.id;
  }
  mapearEdicaoDto() {
    const form = this.formPrincipal.getRawValue();
    return form;
  }

  salvarSistema() {
    this.salvarDados();
  }

}
