import { OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

import { FswCrudPadraoModel } from '../../interface/fsw-crud/fsw-crud-padrao.model';
import { ModoTelaEnum } from '../../enum/modo-tela.enum';
import { FswServicoHttpPadraoModel } from '../../interface/fsw-crud/fsw-servico-http-padrao.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { share } from 'rxjs/operators';

export abstract class FswCrudComponentModel implements FswCrudPadraoModel, OnInit, OnDestroy {
  _pathPesquisar: string;
  _pathCadastrar: string;
  _pathEditar: string;

  private _titulo: string;

  modoTela: ModoTelaEnum = ModoTelaEnum.CADASTRAR;
  subscriptions: Subscription[] = [];
  formPrincipal: FormGroup;
  estadoInicialDados: any;
  id: number;

  protected constructor(
    protected router: Router,
    protected activatedRoute: ActivatedRoute,
    protected validacaoService: ValidacaoFormularioService,
    private servicePrincipal: Partial<FswServicoHttpPadraoModel>,
    protected ngcAlert: NgcAlertService,
    protected fabricaModalService: FabricaModalService
  ) {
    this.verificarTelaVisualizar();
  }

  ngOnInit() {
    this.criarFormulario();
    this.verificarParametroId();
    this.verificarPaths();
  }

  /**
   * @description Verifica o path do modulo atual
   */
  protected verificarPaths() {
    const parentRouter = this.servicePrincipal.moduleParentRouter;
    if (!(this._pathEditar && this._pathCadastrar && this._pathPesquisar) && parentRouter) {
      this._pathPesquisar = parentRouter;
      this._pathCadastrar = parentRouter + '/cadastrar';
      this._pathEditar = parentRouter + '/editar';
    } else {
      console.error('Definir paths padrão');
    }
  }

  /**
   * @description Cria o formulario reativo
   */
  abstract criarFormulario(): void;

  /**
   * @description Faz as validações do formulario reativo e chama o metodo de salvar / editar dados no service
   */
  salvarDados() {
    try {
      this.validacaoService.validarFormulario(this.formPrincipal);
      this.modoTela === ModoTelaEnum.EDITAR ? this.editarDados() : this.cadastrarDados();
    } catch (error) {
      this.ngcAlert.error(error.message);
    }
  }

  /**
   * @description Abre modal para confirmar a alteração dos dados, envia os dados atuais para o backend e recupera os dados alterados
   */
  editarDados() {
    this.fabricaModalService.modalConfirmarAlterar().onActionEvt.subscribe(confirm => {
      if (confirm) {
        this.servicePrincipal.atualizarDados(this.id, this.mapearEdicaoDto())
          .subscribe(() => this.recuperarDados(this.id));
      }
    });
  }

  /**
   * @description Envia os dados atuais para serem cadastrados no backend
   */
  cadastrarDados() {
    this.servicePrincipal.cadastrarDados(this.mapearCadastroDto()).subscribe(() => this.formPrincipal.markAsPristine());
  }

  /**
   * @description Abre modal de confirmação para exclusão do registro. Se confirmado, exclui e redireciona para pagina de consulta
   */
  excluirDados() {
    this.subscriptions.push(
      this.fabricaModalService.modalConfirmarExcluir().onActionEvt.subscribe(confirm => {
        if (confirm) {
          this.servicePrincipal.removerDados(this.id).subscribe(() => {
            this.servicePrincipal.tempFiltroPesquisa = null;
            this.irPara(this._pathPesquisar);
          });
        }
      }));
  }

  verificarParametroId(): void {
    const id = this.activatedRoute.snapshot.params.id;

    if (id) {
      this.modoTela = this.modoTela === ModoTelaEnum.VISUALIZAR ? ModoTelaEnum.VISUALIZAR : ModoTelaEnum.EDITAR;
      this.id = id;
      this.recuperarDados(id);
    }
  }

  verificarTelaVisualizar() {
    this.router.events.subscribe(events => {
      if (events instanceof NavigationEnd && events.url.split('/')[3] === 'visualizar') {
        this.modoTela = ModoTelaEnum.VISUALIZAR;
      }
    });
  }

  abstract mapearCadastroDto(): any;

  abstract mapearEdicaoDto(): any;
  abstract mapearDto(dados: any): any;

  /**
   * @description Requisita ao backend dados de um registro atraves de um id
   * @param id Identificacao numerica que permite a busca do registro no backend
   */
  recuperarDados(id: number) {
    this.servicePrincipal.recuperarDados(id).pipe(share()).subscribe(dadosRecuperados => {
      this.formPrincipal.patchValue(this.mapearDto(dadosRecuperados));
      this.formPrincipal.markAsPristine();
    });
  }

  irPara(path: string): void {
    this.router.navigate([path]);
  }

  voltar() {
    if (!this.formPrincipal.dirty) {
      this.irPara(this._pathPesquisar);
    } else {
      this.subscriptions.push(
        this.fabricaModalService.modalConfirmarVoltar().onActionEvt.subscribe(confirm => {
          if (confirm) {
            this.irPara(this._pathPesquisar);
          }
        })
      );
    }
  }

  exibirCancelar(): boolean {
    return this.formPrincipal.pristine;
  }

  get titulo(): string {
    return this._titulo;
  }

  set titulo(tituloCSU: string) {
    this._titulo = tituloCSU;
  }

  ngOnDestroy(): void {
    if (this.subscriptions && this.subscriptions.length > 0) {
      this.subscriptions.forEach(subscription => subscription.unsubscribe());
    }
  }
}
