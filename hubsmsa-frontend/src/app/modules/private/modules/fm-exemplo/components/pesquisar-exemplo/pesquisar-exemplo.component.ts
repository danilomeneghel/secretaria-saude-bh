import { Component, OnInit } from '@angular/core';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ExemploService } from '../../services/exemplo.service';
import { PesquisarExemploDtoModel } from '../../models/dtos/pesquisar-exemplo-dto.model';
import { AppUtilService } from '@core/services/app-util.service';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { formaCadastroEnumMensagem } from '@shared/models/enum/forma-cadastro.enum';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

@Component({
  selector: 'sbapp-pesquisar-exemplo',
  templateUrl: './pesquisar-exemplo.component.html',
  styleUrls: ['./pesquisar-exemplo.component.css']
})
export class PesquisarExemploComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {
  formPesquisa: FormGroup;
  arquivoEnum = ArquivoEnum;
  dataSourceStatusRadio: InputListaRadioModel[] = AppUtilService.valoresPadraoInputRadioStatus();

  resultadoPaginaAtual: PaginaResultadoPiweb<PesquisarExemploDtoModel>;
  statusEnumMsg = statusEnumMensagem;
  formaCadastroMsg = formaCadastroEnumMensagem;
  permissaoEnum = PermissaoUsuarioEnum;

  constructor(
    private formBuilder: FormBuilder,
    exemploService: ExemploService,
    validacaoService: ValidacaoFormularioService,
    ngcAlert: NgcAlertService
  ) {
    super(validacaoService, exemploService, ngcAlert);
  }

  btnPesquisar(pagina?: PiwebPaginaEvt): void {
    this.pesquisarDados(pagina);
  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      codigo: [],
      nomeExemplo: [],
      status: ['A'],

      ordem: [],
      coluna: [],
      pagina: [],
      itensPorPagina: []
    });
  }

  limpar() {
    this.formPesquisa.reset({ status: 'A' });
    this.resultadoPaginaAtual = null;
    this.paginaAtualCfg = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  }

}
