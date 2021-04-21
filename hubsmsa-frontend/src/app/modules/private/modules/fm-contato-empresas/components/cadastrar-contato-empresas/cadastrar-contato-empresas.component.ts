import { Component, OnInit } from '@angular/core';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { Observable } from 'rxjs';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { ContatoEmpresaService } from '../../services/contato-empresa.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { ContatoEmpresa } from '../../models/contato-empresa.model';
import { ValidacaoFormularioValidators } from '@shared/modules/modules/validacao-formulario/model/funcoes-validacao.model';
import { AppUtilService } from '@core/services/app-util.service';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
const CAMPOS = {
  idEmpresa: 'idEmpresa',
  nome: 'nome',
  email: 'email',
  telefone: 'telefone',
  setor: 'setor',
  status: 'status',
}
@Component({
  selector: 'sbapp-cadastrar-contato-empresas',
  templateUrl: './cadastrar-contato-empresas.component.html',
  styleUrls: ['./cadastrar-contato-empresas.component.css']
})
export class CadastrarContatoEmpresasComponent extends FswCrudComponentModel implements OnInit {

  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;
  listaEmpresasObs: Observable<SelecaoDTO[]>
  dataSourceStatusRadio: InputListaRadioModel[];
  formPrincipal: FormGroup;
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idContatoEmpresa: number;
  constructor(
    private formBuilder: FormBuilder,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    contatoEmpresaService: ContatoEmpresaService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService,
    private empresaService: EmpresaService
  ) {
    super(router, activatedRoute, validacaoService, contatoEmpresaService, ngcAlert, fabricaModalService);
    this.titulo = 'Cadastro de Contatos';
  }

  ngOnInit() {
    super.ngOnInit();
    this.recuperarId();
    this.preencherEmpresas();
  }

  recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idContatoEmpresa = param['id'];
      if (this.idContatoEmpresa) {
        this.prencherFormulario();
        this.titulo = 'Edição de Contatos';
      }
    })
  }
  preencherEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao();
  }

  mapearDto(dados: ContatoEmpresa) {
    return {
      id: dados.id,
      idEmpresa: dados.empresa.id,
      nome: dados.nome,
      email: dados.email,
      telefone: dados.telefone,
      setor:dados.setor,
      status: dados.status
    }
  }
  private prencherFormulario() {
    this.recuperarDados(this.idContatoEmpresa);
  }

    criarFormulario() {
      this.formPrincipal = this.formBuilder.group({
        [CAMPOS.idEmpresa]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
        [CAMPOS.nome]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
        [CAMPOS.email]: [null, [Validators.required, ValidacaoFormularioValidators.validacaoEmail]],
        [CAMPOS.telefone]: [null, [Validators.required, ValidacaoFormularioValidators.validacaoTelefone]],
        [CAMPOS.setor]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
        [CAMPOS.status]: [ Validators.required]
      });
      this.dataSourceStatusRadio = AppUtilService.valoresPadraoInputRadioStatus();
    }

  mapearCadastroDto() {
    const form: ContatoEmpresa = this.formPrincipal.getRawValue();
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

  salvarContatoEmpresa() {
    this.ngcAlert.clearAll();
    this.salvarDados();
  }

}

