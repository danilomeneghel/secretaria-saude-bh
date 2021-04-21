export enum NotificacaoEnum {
    SUCESSO = 'S',
    FALHA = 'F'
}

export const notificacaoEnumMensagem = {
    [NotificacaoEnum.SUCESSO]: 'Sucesso',
    [NotificacaoEnum.FALHA]: 'Falha',
};
