import { Component } from '@angular/core';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { ExemploService } from '../../services/exemplo.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { AppUtilService } from '@core/services/app-util.service';
import { FswCrudComponentModel } from '@shared/models/class/fsw-crud/fsw-crud-component.model';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { FormaCadastroEnum } from '@shared/models/enum/forma-cadastro.enum';
import { ModoTelaEnum } from '@shared/models/enum/modo-tela.enum';
import { CadastrarExemploDtoModel } from '../../models/dtos/cadastrar-exemplo-dto.model';
import { EditarExemploDtoModel } from '../../models/dtos/editar-exemplo-dto.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

@Component({
  selector: 'sbapp-crud-exemplo',
  templateUrl: './crud-exemplo.component.html',
  styleUrls: ['./crud-exemplo.component.css']
})
export class CrudExemploComponent extends FswCrudComponentModel {
  readonly _pathCadastrar = '/admin/exemplo/cadastrar';
  readonly _pathEditar = '/admin/exemplo/editar/';
  readonly _pathPesquisar = '/admin/exemplo';

  readonly itensPorPaginaPadrao = 20;
  readonly paginaPadrao = 1;

  dataSourceStatusRadio: InputListaRadioModel[] = AppUtilService.valoresPadraoInputRadioStatus();
  formPrincipal: FormGroup;
  resultadoTabelaHistoricoAlteracao: PaginaResultadoPiweb<HistoricoAlteracaoModel>;
  paginaAtual: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  modoTelaEnum = ModoTelaEnum;
  permissaoEnum = PermissaoUsuarioEnum;

  constructor(
    private formBuilder: FormBuilder,
    router: Router,
    validacaoService: ValidacaoFormularioService,
    private exemploService: ExemploService,
    ngcAlert: NgcAlertService,
    activatedRoute: ActivatedRoute,
    fabricaModalService: FabricaModalService
  ) {
    super(router, activatedRoute, validacaoService, exemploService, ngcAlert, fabricaModalService);
    this.titulo = 'Exemplo';
  }

  salvar() {
    this.salvarDados();
  }
  mapearDto(dados: any) {
    return dados;
  }
  excluir() {
    this.excluirDados();
  }

  criarFormulario(): void {
    this.formPrincipal = this.formBuilder.group({
      codigo: ['', Validators.required],
      nomeExemplo: ['', Validators.required],
      status: ['A'],
    });
  }

  recuperarDados(id: number) {
    this.exemploService.recuperarDados(id).subscribe(dadosRecuperados => {
      this.formPrincipal.patchValue(dadosRecuperados);
      this.verificaFormaCadastro(dadosRecuperados.formaCadastro);
    });
    this.recuperarHistoricoAlteracao();
  }

  recuperarHistoricoAlteracao(pagina?: PiwebPaginaEvt) {
    const queryParams: ParamsConsultaDto = pagina || this.paginaAtual;
    this.resultadoTabelaHistoricoAlteracao = null;

    this.exemploService.recuperarHistoricoAlteracao(this.id, queryParams)
      .subscribe(historicoAlteracao => this.resultadoTabelaHistoricoAlteracao = historicoAlteracao);
  }

  mapearCadastroDto(): CadastrarExemploDtoModel {
    return this.formPrincipal.value;
  }

  mapearEdicaoDto(): EditarExemploDtoModel {
    return this.formPrincipal.getRawValue();
  }

  /**
   * @description Limpa os campos "Nome de Exemplo", "Status", e "Codigo" apenas se estiver na pagina de edição
   */
  limpar() {
    const controlCodigo = this.formPrincipal.get('codigo');
    this.modoTela === this.modoTelaEnum.EDITAR
      ? this.formPrincipal.reset({ codigo: controlCodigo.value, status: 'A' }) : this.formPrincipal.reset({ status: 'A' });
  }

  verificaFormaCadastro(formaCadastroAtual: FormaCadastroEnum) {
    if (FormaCadastroEnum.CARGA_INICIAL === formaCadastroAtual || FormaCadastroEnum.CARGA_INICIAL_ALTERADA === formaCadastroAtual) {
      this.formPrincipal.get('codigo').disable();
    }
  }
}
