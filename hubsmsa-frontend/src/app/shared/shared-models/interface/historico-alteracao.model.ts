export interface HistoricoAlteracaoModel {
  dadoAnterior: string;
  dadoAtual: string;
  dataAlteracaoDadoAtual: number | Date;
  usuarioResponsavelAlteracao: string;
  empresa: string;
}
