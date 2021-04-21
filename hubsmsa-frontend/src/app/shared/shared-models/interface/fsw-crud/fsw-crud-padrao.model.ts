import { FswEditarPadraoModel } from './fsw-editar-padrao.model';
import { Subscription } from 'rxjs';
import { FormGroup } from '@angular/forms';
import { ModoTelaEnum } from '../../enum/modo-tela.enum';
import { FswCadastrarPadraoModel } from './fsw-cadastrar-padrao.model';
import { FswVisualizarPadraoModel } from './fsw-visualizar-padrao.model';

export interface FswCrudPadraoModel extends FswEditarPadraoModel, FswCadastrarPadraoModel, FswVisualizarPadraoModel {
  modoTela: ModoTelaEnum;
  subscriptions: Subscription[];
  formPrincipal: FormGroup;
  estadoInicialDados: any;

  criarFormulario(): void;

  salvarDados(): void;

  recuperarDados(id: number): void;

  excluirDados(): void;

  voltar(): void;
}
