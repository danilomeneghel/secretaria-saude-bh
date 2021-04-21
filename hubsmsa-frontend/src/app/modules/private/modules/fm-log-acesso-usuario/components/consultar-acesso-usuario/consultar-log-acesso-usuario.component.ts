import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { Observable } from 'rxjs';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { AcessoUsuario } from '../../models/acesso-usuario.model';
import { LogAcessoUsuarioService } from '../../services/log-acesso-usuario.service';

const CAMPOS = {
  idUsuario: 'idUsuario',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-log-acesso-usuario',
  templateUrl: './consultar-log-acesso-usuario.component.html',
  styleUrls: ['./consultar-log-acesso-usuario.component.css']
})
export class ConsultarLogAcessoUsuarioComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formPesquisa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  listaUsuarios: Observable<SelecaoDTO[]>;
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<AcessoUsuario>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  id: number;

  constructor(
    private formBuilder: FormBuilder,
    protected logAcessoUsuarioService: LogAcessoUsuarioService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService
  ) {
    super(validacaoService, logAcessoUsuarioService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.preencherUsuarios();
  }

  validarFormulario() {
    return !(this.formPesquisa.controls.idUsuario.value == null &&
    this.formPesquisa.controls.dataInicial.value == null &&
    this.formPesquisa.controls.dataFinal.value == null);
  }

  validarPeriodoData() {
    return !(this.formPesquisa.controls.dataFinal.value != null &&
      this.formPesquisa.controls.dataInicial.value > this.formPesquisa.controls.dataFinal.value );
  }

  preencherUsuarios() {
    this.listaUsuarios = this.logAcessoUsuarioService.recuperarSelecao();
  }

  criarFormulario() {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.dataInicial]: [],
      [CAMPOS.dataFinal]: [],
      [CAMPOS.idUsuario]: [],


      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
  }

  btnPesquisar(_pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    if (!this.validarFormulario()) {
      this.ngcAlert.error(MensagemEnum.NECESSARIO_PREENCHER_FILTRO);
    } else if (!this.validarPeriodoData()) {
      this.ngcAlert.error(MensagemEnum.FILTRO_DATA_FINAL_INFERIOR);
    } else {
      this.ngcAlert.clearAll();
      this.recuperarAcessoUsuario(_pagina);
    }

  }


  ordenar($event) {
    this.formPesquisa.patchValue($event);
    this.recuperarAcessoUsuario(this.paginaAtualCfg);
  }

  mapearPesquisa(pagina?: PiwebPaginaEvt): AcessoUsuario {
    const params = this.formPesquisa.getRawValue();
    this.adicionarPagina(params, pagina);
    Object.seal(params);

    return params;
  }

  recuperarAcessoUsuario(pagina?: PiwebPaginaEvt) {
    const paginaAtual = pagina || this.paginaAtualCfg;
    const queryParams = this.formPesquisa.getRawValue();
    const params = queryParams;
    params.pagina = paginaAtual.pagina;
    params.itensPorPagina = paginaAtual.itensPorPagina;
    this.resultadoPaginaAtual = null;
    this.id = this.formPesquisa.controls.idUsuario.value;
    this.logAcessoUsuarioService.recuperarAcessoUsuario(queryParams).subscribe(
      acessoUsuario => this.resultadoPaginaAtual = acessoUsuario
    );
  }

  gerarCsv() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logAcessoUsuarioService.gerarCsv(queryParams);
  }

  gerarExcel() {
    const queryParams = this.formPesquisa.getRawValue();
    this.logAcessoUsuarioService.gerarExcel(queryParams);
  }

}
