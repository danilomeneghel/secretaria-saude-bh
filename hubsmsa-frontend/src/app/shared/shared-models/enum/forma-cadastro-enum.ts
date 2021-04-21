export enum FormaCadastroEnum {
  INICIAL = 'I',
  MANUAL = 'C',
  ALTERADA = 'A'
}

export const formaCadastroEnumMensagem = {
  [FormaCadastroEnum.INICIAL]: 'Carga Inicial',
  [FormaCadastroEnum.MANUAL]: 'Cadastro Manual',
  [FormaCadastroEnum.ALTERADA]: 'Carga Inicial Alterada',
};
