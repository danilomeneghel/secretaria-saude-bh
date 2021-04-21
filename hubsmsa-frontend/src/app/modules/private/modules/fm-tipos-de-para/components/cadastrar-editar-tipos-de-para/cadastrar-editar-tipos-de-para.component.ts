import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { TiposDeParaService } from '../../../../../../shared/shared-services/tipos-de-para.service';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { AppUtilService } from '@core/services/app-util.service';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';


const CAMPOS = {
  nome: 'nome',
  descricao: 'descricao',
  status: 'status'
}
@Component({
  selector: 'sbapp-cadastrar-editar-tipos-de-para',
  templateUrl: './cadastrar-editar-tipos-de-para.component.html',
  styleUrls: ['./cadastrar-editar-tipos-de-para.component.css']
})
export class CadastrarEditarTiposDeParaComponent extends FswCrudComponentModel implements OnInit {
  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;

  dataSourceStatusRadio: InputListaRadioModel[];
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idEmpresa: number;

  constructor(protected formBuilder: FormBuilder,
    protected tiposDeParaService: TiposDeParaService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
    protected fabricaModalService: FabricaModalService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
) {
  super(router, activatedRoute, validacaoService, tiposDeParaService, ngcAlert, fabricaModalService);
  this.titulo = 'Cadastro de Tipos de De/Para';

 }

  ngOnInit() {
    super.ngOnInit();
    this.recuperarId();
  }

  private recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idEmpresa = param['id'];
      if (this.idEmpresa) {
        this.prencherFormulario();
        this.titulo = 'Edição de Tipos de De/Para';
      }
    })
  }

  private prencherFormulario() {
    this.recuperarDados(this.idEmpresa);
  }

  criarFormulario(): void {
    this.formPrincipal = this.formBuilder.group({
      [CAMPOS.nome]: [null, [Validators.required, Validators.maxLength(100)]],
      [CAMPOS.descricao]: [null, [Validators.required, Validators.maxLength(250)]],
      [CAMPOS.status]: [ Validators.required]
    });
    this.dataSourceStatusRadio = AppUtilService.valoresPadraoInputRadioStatus();
  }

  salvarTipoDePara() {
    this.salvarDados();
  }

  mapearCadastroDto() {
    let form: TiposDePara = this.formPrincipal.getRawValue();
    form = this.aplicarTrim(form);
    return form;
  }

  mapearDto(tipoDePara: TiposDePara) {
    return tipoDePara;
  }

  mapearEdicaoDto() {
    let form: TiposDePara = this.formPrincipal.getRawValue();
    form = this.aplicarTrim(form);
    return form;
  }

  private aplicarTrim(obj: any): any {
    for (const key in obj) {
      if (obj.hasOwnProperty(key) && typeof obj[key] === 'string') {
        obj[key] = obj[key].trim();
      }
    }
    return obj;
  }

}
