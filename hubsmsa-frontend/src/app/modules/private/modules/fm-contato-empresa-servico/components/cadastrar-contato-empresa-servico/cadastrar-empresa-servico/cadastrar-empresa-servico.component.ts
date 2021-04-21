import { MensagemEnum } from './../../../../../../../shared/shared-models/enum/mensagem.enum';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { notificacaoEnumMensagem } from '@shared/models/enum/notificacao.enum';
import { ContatoEmpresaServico } from '@shared/models/interface/dtos/contato-empresa-servico.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { Servico } from '@shared/models/interface/dtos/servico.model';
import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioValidators } from '@shared/modules/modules/validacao-formulario/model/funcoes-validacao.model';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { ContatoEmpresaService } from 'app/modules/private/modules/fm-contato-empresas/services/contato-empresa.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { ServicoService } from 'app/shared/shared-services/servico.service';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { Observable } from 'rxjs';
import { ContatoEmpresaServicoDTO } from '../../../models/dto/contato-empresa-servico-dto.model';
import { ContatoEmpresaServicoService } from '../../../services/contato-empresa-servico.service';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  idContatoEmpresa: 'idContatoEmpresa',
  idServico: 'idServico',
  notificacaoSucesso: 'notificacaoSucesso',
  notificacaoFalha: 'notificacaoFalha',
  sistemaPrimario: 'sistemaPrimario',
  sistemaSecundario: 'sistemaSecundario'
}

@Component({
  selector: 'sbapp-cadastrar-empresa-servico',
  templateUrl: './cadastrar-empresa-servico.component.html',
  styleUrls: ['./cadastrar-empresa-servico.component.css']
})
export class CadastrarEmpresaServicoComponent extends FswCrudComponentModel implements OnInit {


  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  listaContatoEmpresaObs: Observable<SelecaoDTO[]>;
  listaSistemaObs: Observable<Sistema[]>;
  listaServicoObs: Observable<Servico[]>;
  opcoesCheckboxNotificacao: InputListaCheckboxModel[];
  notificacaoEnum = notificacaoEnumMensagem;
  formPrincipal: FormGroup;
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idContatoEmpresaServico: number;
  sistemaPrimario: string;
  sistemaSecundario: string;
  servico: Observable<Servico>;
  constructor(
    private formBuilder: FormBuilder,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    private sistemaService: SistemaService,
    private contatoEmpresaService: ContatoEmpresaService,
    contatoEmpresaServicoService: ContatoEmpresaServicoService,
    private servicoService: ServicoService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService,
    private empresaService: EmpresaService
  ) {
    super(router, activatedRoute, validacaoService, contatoEmpresaServicoService, ngcAlert, fabricaModalService);
    this.titulo = 'Cadastro de Associação de Contatos da Empresa a Notificação de Serviço';
   }

  ngOnInit() {
    super.ngOnInit();
    this.ouvirCampoEmpresa();
    this.ouvirCampoServico();
    this.recuperarId();
    this.preencherEmpresas();
    this.preencherServicos();
  }

  ouvirCampoEmpresa() {
    this.formPrincipal.get(CAMPOS.idEmpresa).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampo(valor, CAMPOS.idContatoEmpresa);
        this.recuperarContatosEmpresa(valor);

      }
    );
  }

  ouvirCampoServico() {
    this.formPrincipal.get(CAMPOS.idServico).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.recuperarServico(valor);

      }
    );
  }

  recuperarServico(idServico: number) {
    if (idServico){
      this.servicoService.recuperarDados(idServico).subscribe(dado => {
        this.sistemaPrimario = dado.sistemaPrimario.nome;
        this.sistemaSecundario = dado.sistemaSecundario.nome;
      });
    }
  }

  recuperarContatosEmpresa(idEmpresa: number) {
    if (idEmpresa){
      this.listaContatoEmpresaObs = this.contatoEmpresaService.consultarContatosDaEmpresa(idEmpresa);
    }

  }

  recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idContatoEmpresaServico = param['id'];
      if (this.idContatoEmpresaServico) {
        this.preencherFormulario();
        this.titulo = 'Edição de Associação de Contatos da Empresa a Notificação de Serviço';
      }
    })
  }

  preencherEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao();
  }

  preencherSistemas() {
    this.listaSistemaObs = this.sistemaService.recuperarSistemas();
  }

  preencherServicos() {
    this.listaServicoObs = this.servicoService.recuperarServicos();
  }

  private preencherFormulario() {
    this.recuperarDados(this.idContatoEmpresaServico);
  }

  habilitarCampo(valor: number, nomeCampo: string) {
    if (valor) {
      this.formPrincipal.get(nomeCampo).enable();
    } else {
      this.formPrincipal.get(nomeCampo).disable();
    }
  }


  criarFormulario(): void {
    this.formPrincipal = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [null, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.idContatoEmpresa]: [{ value: null, disabled: true }, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.idServico]: [{ value: null }, [Validators.required, ValidacaoFormularioValidators.validarTrim]],
      [CAMPOS.notificacaoSucesso]: [null],
      [CAMPOS.notificacaoFalha]: [null]
    });
  }
  mapearCadastroDto() {
    const form: ContatoEmpresaServicoDTO = this.formPrincipal.getRawValue();
    for (const key in form) {
      if (form.hasOwnProperty(key) && typeof form[key] === 'string') {
        form[key] = form[key].trim();
      }
    }

    return form;
  }
  mapearEdicaoDto() {
    const form = this.formPrincipal.getRawValue();
    return form;
  }
  mapearDto(dados: ContatoEmpresaServico) {
    this.sistemaPrimario = dados.servico.sistemaPrimario.nome;
    this.sistemaSecundario = dados.servico.sistemaSecundario.nome;
    return {
      idEmpresa: dados.empresa.id,
      idContatoEmpresa: dados.contato.id,
      idServico: dados.servico.id,
      notificacaoSucesso: dados.notificacaoSucesso,
      notificacaoFalha: dados.notificacaoFalha,
    }
  }

  salvarContatoEmpresaServico() {
    if (this.outrosCamposValidos() && this.sucessoNotCheck()
         && this.falhaNotCheck()) {
      this.ngcAlert.error(MensagemEnum.VALIDACAO_NOTIFICACAO);
    }else{
      console.log(this.formPrincipal.controls.notificacaoSucesso.value);
      this.salvarDados();
    }
  }


  private falhaNotCheck() {
    return (this.formPrincipal.controls.notificacaoFalha.value == null ||
      this.formPrincipal.controls.notificacaoFalha.value == false);
  }

  private sucessoNotCheck() {
    return (this.formPrincipal.controls.notificacaoSucesso.value == null ||
      this.formPrincipal.controls.notificacaoSucesso.value == false);
  }

  private outrosCamposValidos() {
    return this.formPrincipal.controls.idEmpresa.value != null &&
      this.formPrincipal.controls.idContatoEmpresa.value != null &&
      this.formPrincipal.controls.idServico.value != null;
  }
}
