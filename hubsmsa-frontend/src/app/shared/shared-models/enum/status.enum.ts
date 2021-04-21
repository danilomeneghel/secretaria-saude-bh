export enum StatusEnum {
    ATIVO = 'A',
    INATIVO = 'I'
}

export const statusEnumMensagem = {
    [StatusEnum.ATIVO]: 'Ativo',
    [StatusEnum.INATIVO]: 'Inativo',
};

export const statusEnumMensagemVisualizar = {
    [StatusEnum.ATIVO]: 'Sim',
    [StatusEnum.INATIVO]: 'Não',
};