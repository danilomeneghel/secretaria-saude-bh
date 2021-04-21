export enum StatusExecucaoEnum {
    SUCESSO = 'S',
    FALHA = 'F'
}

export const statusExecucaoEnumMensagem = {
    [StatusExecucaoEnum.SUCESSO]: 'Sucesso',
    [StatusExecucaoEnum.FALHA]: 'Falha',
};
