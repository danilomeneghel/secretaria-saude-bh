import { OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { Subscription } from 'rxjs';

import { ModoTelaEnum } from '../../enum/modo-tela.enum';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';

export abstract class FswVisualizarComponentModel implements FswVisualizarPadraoModel, OnInit, OnDestroy {
  abstract _pathPesquisar: string;

  private _titulo: string;

  modoTela: ModoTelaEnum = ModoTelaEnum.CADASTRAR;
  subscriptions: Subscription[] = [];
  formPrincipal: FormGroup;
  id: number;

  protected constructor(
    protected router: Router,
    protected activatedRoute: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.verificarParametroId();
    this.verificarPaths();
  }

  private verificarPaths() {
    if (!(this._pathPesquisar)) {
      console.error('Definir paths padrão');
    }
  }

  verificarParametroId(): void {
    const id = this.activatedRoute.snapshot.params.id;
    if (id) {
      this.modoTela = ModoTelaEnum.VISUALIZAR;
      this.id = id;
      this.recuperarDados(id);
    }
  }

  abstract recuperarDados(id: number): void;

  irPara(path: string): void {
    this.router.navigate([path]);
  }

  get titulo(): string {
    return this.modoTela + ' ' + this._titulo;
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
