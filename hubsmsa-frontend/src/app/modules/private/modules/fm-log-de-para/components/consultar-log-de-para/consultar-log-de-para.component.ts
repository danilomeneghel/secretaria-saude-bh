import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { UsuarioService } from './../../../../../../shared/shared-services/usuario.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { LogDePara } from '@shared/models/interface/dtos/log-de-para';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { dataSourceCheckboxStatus } from '@shared/models/constants';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { resetInputListCheckbox } from '@shared/components/components/input-lista-checkbox/util/input-list-checkbox-utils';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { LogDeParaService } from '../../services/log-de-para.service';

const CAMPOS = {
  idTipoDePara: 'idTipoDePara',
  nome: 'nomeDePara',
  idUsuario: 'idUsuario',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',
  idEmpresaPrimaria: 'idEmpresaPrimaria',
  idSistemaPrimario: 'idSistemaPrimario',
  idEmpresaSecundaria: 'idEmpresaSecundaria',
  idSistemaSecundario: 'idSistemaSecundario',
  ordem: 'ordem',
  coluna: 'coluna',
  pagina: 'pagina',
  itensPorPagina: 'itensPorPagina'
}
@Component({
  selector: 'sbapp-consultar-log-de-para',
  templateUrl: './consultar-log-de-para.component.html',
  styleUrls: ['./consultar-log-de-para.component.css']
})
export class ConsultarLogDeParaComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formPesquisa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<LogDePara>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  listaTipoDeParaObs: Observable<TiposDePara[]>;
  listaUsuarios: Observable<SelecaoDTO[]>;
  listaEmpresasObs: Observable<Empresa[]>;
  listaSistemasPriObs: Observable<SelecaoDTO[]>;
  listaSistemasSecObs: Observable<SelecaoDTO[]>;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  id: number;

  constructor(
    private formBuilder: FormBuilder,
    private empresaService: EmpresaService,
    private tipoDePara: TiposDeParaService,
    protected logDeParaService: LogDeParaService,
    protected usuarioService: UsuarioService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService
  ) {
    super(validacaoService, logDeParaService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.recuperarTiposDePara();
    this.recuperarEmpresas();
    this.preencherUsuarios();
    this.ouvirCampoEmpresa();
  }

  ouvirCampoEmpresa() {
    this.formPesquisa.get(CAMPOS.idEmpresaPrimaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.ngcAlert.clearAll();
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaPrimario);
        this.recuperarSistemasPri(valor);
      }
    );

    this.formPesquisa.get(CAMPOS.idEmpresaSecundaria).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.ngcAlert.clearAll();
        this.habilitarCampoSistema(valor, CAMPOS.idSistemaSecundario);
        this.recuperarSistemasSec(valor);
      }
    );
  }

  habilitarCampoSistema(valor: number, nomeCampo: string) {
    if (valor) {
      this.formPesquisa.get(nomeCampo).enable();
    } else {
      this.formPesquisa.get(nomeCampo).disable();
    }
  }

  validarFormulario() {
    return !(this.formPesquisa.controls.idSistemaPrimario.value == null &&
      this.formPesquisa.controls.idSistemaSecundario.value == null &&
      this.formPesquisa.controls.dataInicial.value == null &&
      this.formPesquisa.controls.dataFinal.value == null &&
      this.formPesquisa.controls.nomeDePara.value == null &&
      this.formPesquisa.controls.idTipoDePara.value == null &&
      this.formPesquisa.controls.idUsuario.value == null) ;
  }

  validarPeriodoData() {
    return !(this.formPesquisa.controls.dataFinal.value != null &&
      this.formPesquisa.controls.dataInicial.value > this.formPesquisa.controls.dataFinal.value );
  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],
      [CAMPOS.idTipoDePara]: [],
      [CAMPOS.nome]: [null, Validators.maxLength(100)],
      [CAMPOS.idUsuario]: [],
      [CAMPOS.idEmpresaPrimaria]: [],
      [CAMPOS.idSistemaPrimario]: [{ value: null, disabled: true }],
      [CAMPOS.idEmpresaSecundaria]: [],
      [CAMPOS.idSistemaSecundario]: [{ value: null, disabled: true }],
      [CAMPOS.ordem]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.itensPorPagina]: []
    });
    this.opcoesCheckboxStatus = dataSourceCheckboxStatus;
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    const params = this.mapearPesquisa(_pagina);
    if (!this.validarFormulario()) {
      this.ngcAlert.error(MensagemEnum.NECESSARIO_PREENCHER_FILTRO);
    } else if (!this.validarPeriodoData()) {
      this.ngcAlert.error(MensagemEnum.FILTRO_DATA_FINAL_INFERIOR);
    } else {
      this.ngcAlert.clearAll();
      this.pesquisarDados(_pagina, params);
    }
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

  recuperarTiposDePara() {
    this.listaTipoDeParaObs = this.tipoDePara.pesquisarDados(null).pipe(
      map((listaEmpresas) => {
        return listaEmpresas.itens;
      }
      ));
  }

  preencherUsuarios() {
    this.listaUsuarios = this.usuarioService.recuperarSelecao();
  }

  recuperarEmpresas() {
    this.listaEmpresasObs = this.empresaService.pesquisarDadosDroplist(null).pipe(
      map((listaEmpresas) => {
        return listaEmpresas.itens;
      }
      ));
  }

  recuperarSistemasPri(idEmpresa: number) {
    if (idEmpresa) {
      this.listaSistemasPriObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);
    }

  }

  recuperarSistemasSec(idEmpresa: number) {
    if (idEmpresa) {
      this.listaSistemasSecObs = this.empresaService.consultarSistemasDaEmpresa(idEmpresa);
    }
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logDeParaService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logDeParaService.gerarExcel(queryParams);
  }

}
