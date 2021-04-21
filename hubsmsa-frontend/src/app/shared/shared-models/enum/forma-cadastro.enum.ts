export enum FormaCadastroEnum {
    CARGA_INICIAL = 'I',
    CARGA_INICIAL_ALTERADA = 'A',
    CARGA_MANUAL = 'C'
}

export const formaCadastroEnumMensagem = {
    [FormaCadastroEnum.CARGA_INICIAL]: 'Carga Inicial',
    [FormaCadastroEnum.CARGA_INICIAL_ALTERADA]: 'Carga Inicial Alterada',
    [FormaCadastroEnum.CARGA_MANUAL]: 'Cadastro Manual'
};

