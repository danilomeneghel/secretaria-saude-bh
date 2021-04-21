import { Component, OnInit } from '@angular/core';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { PaginaResultadoPiweb, PiwebPaginaEvt, TabelaPiwebOrdemEvtModel } from '@shared/modules/modules/piweb-tabela';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { DeParaService } from '../../services/de-para.service';
import { FormBuilder, Validators, FormArray, FormGroup } from '@angular/forms';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Observable } from 'rxjs';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { map } from 'rxjs/operators';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { CampoSistemaDTO } from '../../models/campo-sistema-dto.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { ColunasOrdenacaoDeParaEnum } from '../../models/enums/ordenacao-depara-enum';
import { FiltrosDeParaDTO } from '../../models/dtos/pesquisa/filtros-depara-dto.model';
import { GridPesquisaDeParaDTO } from '../../models/dtos/pesquisa/grid-pesquisa-depara-dto.model';


const CAMPOS = {
  idTipoDePara: 'idTipoDePara',
  nome: 'nomeDePara',
  status: 'status',
  idEmpresaPrimaria: 'idEmpresaPrimaria',
  idSistemaPrimario: 'idSistemaPrimario',
  idEmpresaSecundaria: 'idEmpresaSecundaria',
  idSistemaSecundario: 'idSistemaSecundario',
  listaDeParaPrimario: 'listaDeParaPrimario',
  listaDeParaSecundario: 'listaDeParaSecundario',
  codigo: 'codigo',
  descricao: 'descricao',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}
@Component({
  selector: 'sbapp-consultar-de-para',
  templateUrl: './consultar-de-para.component.html',
  styleUrls: ['./consultar-de-para.component.css']
})
export class ConsultarDeParaComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<GridPesquisaDeParaDTO>;
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  listaTipoDeParaObs: Observable<TiposDePara[]>;
  listaEmpresasObs: Observable<Empresa[]>;
  listaSistemasPriObs: Observable<SelecaoDTO[]>;
  listaSistemasSecObs: Observable<SelecaoDTO[]>;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  constructor(
    private deParaService: DeParaService,
    private tipoDePara: TiposDeParaService,
    private empresaService: EmpresaService,
    private formBuilder: FormBuilder,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
    private fabricaModalService: FabricaModalService) {
    super(validacaoService, deParaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.ouvirCampoEmpresa();
    this.recuperarTiposDePara();
    this.recuperarEmpresas();
  }

  ouvirCampoEmpresa() {
    this.formPesquisa.get(CAMPOS.idEmpresaPrimaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaPrimario);
        this.recuperarSistemasPri(valor);
      }
    );

    this.formPesquisa.get(CAMPOS.idEmpresaSecundaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaSecundario);
        this.recuperarSistemasSec(valor);
      }
    );
  }

  recuperarTiposDePara() {
    this.listaTipoDeParaObs = this.tipoDePara.pesquisarDados(null).pipe(
      map((listaEmpresas) => {
        return listaEmpresas.itens;
      }
      ));
  }

  recuperarEmpresas() {
    this.listaEmpresasObs = this.empresaService.pesquisarDadosDroplist(null).pipe(
      map((listaEmpresas) => {
        return listaEmpresas.itens;
      }
      ));
  }

  recuperarSistemasPri(idEmpresa: number) {
    if (idEmpresa)
      this.listaSistemasPriObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);

  }

  recuperarSistemasSec(idEmpresa: number) {
    if (idEmpresa)
      this.listaSistemasSecObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);
  }

  habilitarCampoSistema(valor: number, nomeCampo: string) {
    if (valor) {
      this.formPesquisa.get(nomeCampo).enable();
    } else {
      this.formPesquisa.get(nomeCampo).disable();
    }
  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idTipoDePara]: [],
      [CAMPOS.nome]: [null, Validators.maxLength(100)],
      [CAMPOS.status]: [],
      [CAMPOS.idEmpresaPrimaria]: [],
      [CAMPOS.idSistemaPrimario]: [{ value: null, disabled: true }],
      [CAMPOS.listaDeParaPrimario]: this.formBuilder.array([this.criarCodigoDescricao()]),

      [CAMPOS.idEmpresaSecundaria]: [],
      [CAMPOS.idSistemaSecundario]: [{ value: null, disabled: true }],
      [CAMPOS.listaDeParaSecundario]: this.formBuilder.array([this.criarCodigoDescricao()]),


      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    const params = this.adicionarPagina(this._mapearPesquisa(), pagina);
    this.pesquisarDados(pagina, params);
  }

  private _mapearPesquisa(): FiltrosDeParaDTO {

    const camposPrimarios = this._maperarCamposPrimarios(CAMPOS.listaDeParaPrimario);
    const camposSecundarios = this._maperarCamposPrimarios(CAMPOS.listaDeParaSecundario);

    return {
      idTipoDePara: this.formPesquisa.get(CAMPOS.idTipoDePara).value,
      nomeDePara: this.formPesquisa.get(CAMPOS.nome).value,
      status: this.formPesquisa.get(CAMPOS.status).value,
      sistemaPrimario: {
        idEmpresa: this.formPesquisa.get(CAMPOS.idEmpresaPrimaria).value,
        idSistema: this.formPesquisa.get(CAMPOS.idSistemaPrimario).value,
        campos: camposPrimarios
      },
      sistemaSecundario: {
        idEmpresa: this.formPesquisa.get(CAMPOS.idEmpresaSecundaria).value,
        idSistema: this.formPesquisa.get(CAMPOS.idSistemaSecundario).value,
        campos: camposSecundarios
      }
    }

  }

  private _maperarCamposPrimarios(formControlName): CampoSistemaDTO[] {
    const camposSistema = this.formPesquisa.get(formControlName).value;
    return camposSistema.map(campo => {
      return {
        codigo: campo.codigo || null,
        descricao: campo.descricao || null
      };
    }).filter(campo =>
      campo.codigo !== null || campo.descricao !== null
    );
  }

  limpar() {
    this.ngcAlert.clearAll();
    this.formPesquisa.reset();
    resetInputListCheckbox(this.opcoesCheckboxStatus);

    this.resultadoPaginaAtual = null;
    this.paginaAtualCfg = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  }

  ordenar(ordemEvt: TabelaPiwebOrdemEvtModel) {
    this.ngcAlert.clearAll();
    const orderObj = {
      coluna: ColunasOrdenacaoDeParaEnum[ordemEvt.coluna],
      ordem: ordemEvt.ordem
    };
    const params = Object.assign({},
      this._mapearPesquisa(),
      this.paginaAtualCfg,
      orderObj);
    Object.seal(params);
    this.servicePrincipal.pesquisarDados(params).subscribe(response => {
      this.resultadoPaginaAtual = response;
    });
  }

  excluirDePara(idDePara: number) {
    this.fabricaModalService.modalConfirmarExcluirRegistro().onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.deParaService.removerDados(idDePara).subscribe(() => {
          const params = this.adicionarPagina(this._mapearPesquisa(), null);
          this.pesquisarDados(null, params);
        });
      }
    });
  }
  addDeParaPrimario() {
    const listaDeParaPrimario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaPrimario) as FormArray;
    listaDeParaPrimario.push(this.criarCodigoDescricao());
  }

  addDeParaSecundario() {
    const listaDeParaSecundario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaSecundario) as FormArray;
    listaDeParaSecundario.push(this.criarCodigoDescricao());
  }
  criarCodigoDescricao(): FormGroup {
    const codigoDescricao = this.formBuilder.group({
      [CAMPOS.codigo]: [null, [Validators.maxLength(10)]],
      [CAMPOS.descricao]: [null, [Validators.maxLength(100)]],
    })
    return codigoDescricao;
  }
  tamanhoDoFormularioSecundario() {
    const listaDeParaSecundario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaSecundario) as FormArray;
    return listaDeParaSecundario.length;
  }
  tamanhoDoFormularioPrimario() {
    const listaDeParaPrimario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaPrimario) as FormArray;
    return listaDeParaPrimario.length;
  }

  removerDeParaPrimario(i: number) {
    const listaDeParaPrimario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaPrimario) as FormArray;
    listaDeParaPrimario.length > 1 ? listaDeParaPrimario.removeAt(i) : listaDeParaPrimario.controls[i].reset();
  }
  removerDeParaSecundario(i: number) {
    const listaDeParaSecundario: FormArray = this.formPesquisa.get(CAMPOS.listaDeParaSecundario) as FormArray;
    listaDeParaSecundario.length > 1 ? listaDeParaSecundario.removeAt(i) : listaDeParaSecundario.controls[i].reset();
  }

  gerarCsv(pagina?: PiwebPaginaEvt): void  {
    const params = this.adicionarPagina(this._mapearPesquisa(), pagina);
    this.deParaService.gerarDeParaCsv(params);
  }

  gerarExcel(pagina?: PiwebPaginaEvt): void   {
    const params = this.adicionarPagina(this._mapearPesquisa(), pagina);
    this.deParaService.gerarDeParaExcel(params);
  }
}
