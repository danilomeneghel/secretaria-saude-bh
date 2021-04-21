export enum MotivoAlteracaoEnum {
  MUDANCA = 'M',
  ERRO = 'E',
  OUTRO = 'O'
}

export const motivoAlteracaoEnumMensagem = {
  [MotivoAlteracaoEnum.MUDANCA]: 'Mudança de descrição / Status',
  [MotivoAlteracaoEnum.ERRO]: 'Erro',
  [MotivoAlteracaoEnum.OUTRO]: 'Outros'
};
