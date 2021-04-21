import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AppUtilService } from '@core/services/app-util.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { Observable } from 'rxjs';
import { share } from 'rxjs/operators';
import { DeParaService } from '../../services/de-para.service';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { CadastroDeParaDTO } from '../../models/dtos/cadastro/cadastro-depara-dto.model';
import { CampoSistemaDTO } from '../../models/campo-sistema-dto.model';

const CAMPOS = {
  idTipoDePara: 'idTipoDePara',
  nome: 'nome',
  status: 'status',
  listaDeParaPrimario: 'listaDeParaPrimario',
  listaDeParaSecundario: 'listaDeParaSecundario',
  idEmpresaPrimaria: 'idEmpresaPrimaria',
  idSistemaPrimario: 'idSistemaPrimario',
  idEmpresaSecundaria: 'idEmpresaSecundaria',
  idSistemaSecundario: 'idSistemaSecundario',
  id : 'id',
  codigo: 'codigo',
  descricao: 'descricao',
}
@Component({
  selector: 'sbapp-cadastrar-editar-de-para',
  templateUrl: './cadastrar-editar-de-para.component.html',
  styleUrls: ['./cadastrar-editar-de-para.component.css']
})
export class CadastrarEditarDeParaComponent extends FswCrudComponentModel implements OnInit {

  listaTipoDeParaObs: Observable<SelecaoDTO[]>;
  listaEmpresasObs: Observable<SelecaoDTO[]>;
  listaSistemasPriObs: Observable<SelecaoDTO[]>;
  listaSistemasSecObs: Observable<SelecaoDTO[]>;

  listaDeParaPrimario: CampoSistemaDTO[] = [];
  listaDeParaSecundario: CampoSistemaDTO[] = [];

  dataSourceStatusRadio: InputListaRadioModel[];
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;
  idDePara: number;
  formPrincipal: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    protected deParaService: DeParaService,
    private tipoDePara: TiposDeParaService,
    private empresaService: EmpresaService,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService,
  ) {
    super(router, activatedRoute, validacaoService, deParaService, ngcAlert, fabricaModalService);
    this.titulo = 'Cadastro de De/Para';
  }

  ngOnInit() {
    this.recuperarId();
    super.ngOnInit();
    this.ouvirCampoEmpresa();
    this.recuperarTiposDePara();
    this.recuperarEmpresas();
  }

  recuperarId() {
    this.activatedRoute.params.subscribe((param: Params) => {
      this.idDePara = param['id'];
      if (this.idDePara) {
        this.modoTela = ModoTelaEnum.EDITAR;
        this.titulo = 'Edição de De/Para';
      }
    })
  }

  ouvirCampoEmpresa() {
    this.formPrincipal.get(CAMPOS.idEmpresaPrimaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaPrimario);
        this.recuperarSistemasPri(valor);
      }
    );

    this.formPrincipal.get(CAMPOS.idEmpresaSecundaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaSecundario);
        this.recuperarSistemasSec(valor);
      }
    );
  }

  habilitarCampoSistema(valor: number, nomeCampo: string) {
    if (valor) {
      this.formPrincipal.get(nomeCampo).enable();
    } else {
      this.formPrincipal.get(nomeCampo).disable();
    }
  }

  recuperarTiposDePara() {
    this.listaTipoDeParaObs = this.tipoDePara.recuperarSelecaoTipoDePara();
  }

  recuperarEmpresas() {
    this.listaEmpresasObs = this.empresaService.recuperarSelecao().pipe(share());
  }

  recuperarSistemasPri(idEmpresa: number) {
    if (idEmpresa) this.listaSistemasPriObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);
  }

  recuperarSistemasSec(idEmpresa: number) {
    if (idEmpresa) this.listaSistemasSecObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);
  }

  criarFormulario(): void {

    const deParaPrimarioFormArray = this.modoTela === ModoTelaEnum.EDITAR ?
      this.formBuilder.array([]) : this.formBuilder.array([this.criarCodigoDescricao()]);
    const deParaSecundarioFormArray = this.modoTela === ModoTelaEnum.EDITAR ?
      this.formBuilder.array([]) : this.formBuilder.array([this.criarCodigoDescricao()]);

    this.formPrincipal = this.formBuilder.group({
      [CAMPOS.idTipoDePara]: [null, Validators.required],
      [CAMPOS.nome]: [null, [Validators.required, Validators.maxLength(100)]],
      [CAMPOS.status]: [Validators.required],
      [CAMPOS.idEmpresaPrimaria]: [null, Validators.required],
      [CAMPOS.idSistemaPrimario]: [{ value: null, disabled: true }, Validators.required],
      [CAMPOS.listaDeParaPrimario]: deParaPrimarioFormArray,
      [CAMPOS.idEmpresaSecundaria]: [null, Validators.required],
      [CAMPOS.idSistemaSecundario]: [{ value: null, disabled: true }, Validators.required],
      [CAMPOS.listaDeParaSecundario]: deParaSecundarioFormArray
    });
    this.dataSourceStatusRadio = AppUtilService.valoresPadraoInputRadioStatus();
  }


  mapearCadastroDto() {
    let form: CadastroDeParaDTO = this._mapearDeParaDTO();
    form = this.aplicarTrim(form);
    return form;
  }

  mapearEdicaoDto() {
    let form: CadastroDeParaDTO = this._mapearDeParaDTO();
    form = this.aplicarTrim(form);
    this.resetCamposDeParaFormArray();
    return form;
  }

  private resetCamposDeParaFormArray() {
    const deParaPrimario = this.formPrincipal.get(CAMPOS.listaDeParaPrimario) as FormArray;
    const deParaSecundario = this.formPrincipal.get(CAMPOS.listaDeParaSecundario) as FormArray;
    while (deParaPrimario.length !== 0) {
      deParaPrimario.removeAt(0);
    }
    while (deParaSecundario.length !== 0) {
      deParaSecundario.removeAt(0);
    }
  }

  private _mapearDeParaDTO(): CadastroDeParaDTO {
    const camposPrimarios = this._maperarCamposSistema(CAMPOS.listaDeParaPrimario);
    const camposSecundarios = this._maperarCamposSistema(CAMPOS.listaDeParaSecundario);

    console.log(camposPrimarios);


    return {
      idTipoDePara: this.formPrincipal.get(CAMPOS.idTipoDePara).value,
      nomeDePara: this.formPrincipal.get(CAMPOS.nome).value,
      status: this.formPrincipal.get(CAMPOS.status).value,
      sistemaPrimario: {
        idEmpresa: this.formPrincipal.get(CAMPOS.idEmpresaPrimaria).value,
        idSistema: this.formPrincipal.get(CAMPOS.idSistemaPrimario).value,
        camposDePara: camposPrimarios
      },
      sistemaSecundario: {
        idEmpresa: this.formPrincipal.get(CAMPOS.idEmpresaSecundaria).value,
        idSistema: this.formPrincipal.get(CAMPOS.idSistemaSecundario).value,
        camposDePara: camposSecundarios
      }
    };
  }

  mapearDto(deParaDTO: CadastroDeParaDTO) {

    const deParaPrimario = this.formPrincipal.get(CAMPOS.listaDeParaPrimario) as FormArray;
    const deParaSecundario = this.formPrincipal.get(CAMPOS.listaDeParaSecundario) as FormArray;

    deParaDTO.sistemaPrimario.camposDePara.map(item => {
      return {
        [CAMPOS.id]: [item.id, []],
        [CAMPOS.codigo]: [item.codigo, [Validators.required, Validators.maxLength(10)]],
        [CAMPOS.descricao]: [item.descricao, [Validators.required, Validators.maxLength(100)]]
      };
    }).forEach(item => {
      deParaPrimario.push(this.formBuilder.group(item));
    });

    deParaDTO.sistemaSecundario.camposDePara.map(item => {
      return {
        [CAMPOS.id]: [item.id, []],
        [CAMPOS.codigo]: [item.codigo, [Validators.required, Validators.maxLength(10)]],
        [CAMPOS.descricao]: [item.descricao, [Validators.required, Validators.maxLength(100)]]
      };
    }).forEach(item => {
      deParaSecundario.push(this.formBuilder.group(item));
    });

    return {
      [CAMPOS.idTipoDePara]: deParaDTO.idTipoDePara,
      [CAMPOS.nome]: deParaDTO.nomeDePara,
      [CAMPOS.status]: deParaDTO.status,
      [CAMPOS.idEmpresaPrimaria]: deParaDTO.sistemaPrimario.idEmpresa,
      [CAMPOS.idSistemaPrimario]: deParaDTO.sistemaPrimario.idSistema,
      [CAMPOS.listaDeParaPrimario]: deParaDTO.sistemaPrimario.camposDePara,
      [CAMPOS.listaDeParaSecundario]: deParaDTO.sistemaSecundario.camposDePara,
      [CAMPOS.idEmpresaSecundaria]: deParaDTO.sistemaSecundario.idEmpresa,
      [CAMPOS.idSistemaSecundario]: deParaDTO.sistemaSecundario.idSistema,
    };
  }

  /*mapearDto(dePara: DePara) {

    this.listaDeParaPrimario = dePara.deParaPrimario;
    this.listaDeParaSecundario = dePara.deParaSecundario;

    for (let index = 0; index < dePara.deParaPrimario.length; index++) {
      if (dePara.deParaPrimario.length > this.tamanhoDoFormularioPrimario()) {
        this.addDeParaPrimario();
      }
    }
    for (let index = 0; index < dePara.deParaSecundario.length; index++) {
      if (dePara.deParaSecundario.length > this.tamanhoDoFormularioSecundario()) {
        this.addDeParaSecundario();
      }
    }
    return {
      [CAMPOS.idTipoDePara]: dePara.tipoDePara.id,
      [CAMPOS.nome]: dePara.nome,
      [CAMPOS.status]: dePara.status,
      [CAMPOS.idEmpresaPrimaria]: dePara.sistemaPrimario.empresa.id,
      [CAMPOS.idSistemaPrimario]: dePara.sistemaSecundario.id,
      [CAMPOS.listaDeParaPrimario]: dePara.deParaPrimario,
      [CAMPOS.listaDeParaSecundario]: dePara.deParaSecundario,
      [CAMPOS.idEmpresaSecundaria]: dePara.sistemaSecundario.empresa.id,
      [CAMPOS.idSistemaSecundario]: dePara.sistemaSecundario.id,
    };
  }*/

  private aplicarTrim(obj: any): any {
    for (const key in obj) {
      if (obj.hasOwnProperty(key) && typeof obj[key] === 'string') {
        obj[key] = obj[key].trim();
      }
    }
    return obj;
  }

  salvarDePara() {
    this.salvarDados();
  }

  private _maperarCamposSistema(formControlName): CampoSistemaDTO[] {
    const camposSistema = this.formPrincipal.get(formControlName).value;

    return camposSistema.map(campo => {
      return {
        id: campo.id || null,
        codigo: campo.codigo || null,
        descricao: campo.descricao || null
      };
    }).filter(campo =>
       campo.codigo !== null && campo.descricao !== null
    );
  }


  addDeParaPrimario(item?, i?: number): void {
    this.getListaPrimaria().push(this.criarCodigoDescricao());

    if (item && i) {

      if ((i + 1) < this.listaDeParaPrimario.length) {
        this.listaDeParaPrimario[i].id = item.id;
        this.listaDeParaPrimario[i].codigo = item.codigo;
        this.listaDeParaPrimario[i].descricao = item.descricao;
      } else {
        const dePara: CampoSistemaDTO = {
          id: item.id,
          codigo: item.codigo,
          descricao: item.descricao
        }
        this.listaDeParaPrimario.push(dePara);
      }
    }

  }


  addDeParaSecundario(item?, i?: number): void {
    this.getListaSecundario().push(this.criarCodigoDescricao());
    if (item && i) {
      if ((i + 1) < this.listaDeParaSecundario.length) {
        this.listaDeParaSecundario[i].id = item.id;
        this.listaDeParaSecundario[i].codigo = item.codigo;
        this.listaDeParaSecundario[i].descricao = item.descricao;
      } else {
        const dePara: CampoSistemaDTO = {
          id: item.id,
          codigo: item.codigo,
          descricao: item.descricao
        }
        this.listaDeParaSecundario.push(dePara);
      }
    }
  }


  tamanhoDoFormularioSecundario() {
    return this.getListaSecundario().length;
  }
  tamanhoDoFormularioPrimario() {
    return this.getListaPrimaria().length;
  }


  removerDeParaPrimario(i: number) {
    const listaDeParaPrimario: FormArray = this.getListaPrimaria();
    listaDeParaPrimario.length > 1 ? listaDeParaPrimario.removeAt(i) : listaDeParaPrimario.controls[i].reset();
    this.listaDeParaPrimario.splice(i, 1);
  }

  removerDeParaSecundario(i: number) {
    const listaDeParaSecundario: FormArray = this.getListaSecundario();
    listaDeParaSecundario.length > 1 ? listaDeParaSecundario.removeAt(i) : listaDeParaSecundario.controls[i].reset();
    this.listaDeParaSecundario.splice(i, 1);
  }


  private getListaPrimaria(): FormArray {
    return this.formPrincipal.get(CAMPOS.listaDeParaPrimario) as FormArray;
  }
  private getListaSecundario(): FormArray {
    return this.formPrincipal.get(CAMPOS.listaDeParaSecundario) as FormArray;
  }

  criarCodigoDescricao(): FormGroup {
    const codigoDescricao = this.formBuilder.group({
      [CAMPOS.id]: [null, []],
      [CAMPOS.codigo]: [null, [Validators.required, Validators.maxLength(10)]],
      [CAMPOS.descricao]: [null, [Validators.required, Validators.maxLength(100)]],
    })
    return codigoDescricao;
  }

}
