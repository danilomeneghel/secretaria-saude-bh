export interface FswEditarPadraoModel {
  id: number;

  recuperarDados(id: number): void;

  verificarParametroId(): void;

  editarDados(): void;

  exibirCancelar(): void;
}
