export enum MensagemEnum {
  CONFIRMAR_LIMPAR_REGISTROS = 'Tem certeza que deseja limpar todos os registros?',
  CONFIRMAR_VOLTAR = 'Existem modificações pendentes neste formulário. \nTem certeza de que deseja prosseguir sem salvá-las?',
  CONFIRMAR_INATIVACAO = 'Confirme a inativação do registro.',
  CONFIRMAR_EXCLUSAO = 'Confirme a exclusão do registro.',
  ERRO_DESCONHECIDO = 'Erro desconhecido, verifique o console.',
  ERRO_AO_FORMATAR_MENSAGEM = 'Erro ao formatar a mensagem do serviços.',
  SERVICO_INDISPONIVEL = 'Serviço indisponível no momento.',
  ERRO_NO_SERVICO = 'Erro ao consumir o serviço "{VALUE}".',
  ERRO_INTERNO = 'Erro inesperado ao consumir serviço, \nverifique o console.',
  SERVICO_NAO_EXISTE = 'O serviço solicitado não existe. \nVerifique o console.',
  ERRO_AO_REALIZAR_PARSE_JSON = 'Erro ao realizar análise de mensagem recebida do serviço para a aplicação.',
  CONFIRMAR_CANCELAR_FORMULARIO = 'Confirme o cancelamento da edição do registro.',
  CONFIRMAR_INICICAR_FLUXO_NOVO = 'As alterações realizadas não foram salvas. \nAo confirmar a operação as informações serão perdidas.',
  EXCLUIR_ENTIDADE_POSSUI_RELACIONAMENTO = 'O registro não pode ser excluído. O(A) {VALUE} possui vínculo. Para imprimir a lista de vinculações <a class="link-alerta" #click>clique aqui</a>.',
  INATIVAR_ENTIDADE_POSSUI_RELACIONAMENTO = 'Inativação realizada com sucesso! O(A) {VALUE} inativado(a) possui vínculo. É necessário que, em cada registro listado, seja informado(a) um(a) {VALUE} ativo(a). Para imprimir a lista de vinculações <a class="link-alerta" #click>clique aqui</a>.',
  CONFIRMAR_EXCLUSAO_MESTRE_DETALHE = 'Confirme aqui a opção de exclusão do(a) {VALUE}. Esta exclusão só ocorrerá efetivamente quando for acionado o botão <u>SALVAR no registro mestre.</u>',
  CONFIRMAR_EXCLUSAO_REGISTRO = 'Confirma a exclusão do registro selecionado?',
  CONFIRMAR_ALTERACAO_REGISTRO = 'Confirma a alteração do registro selecionado?',
  NAO_POSSUI_PERMISSAO = 'Usuário não possui permissão necessária.',
  PERFIL_NAO_AUTORIZADO = 'Usuário não possui perfil necessário.',
  ASSOCIACAO_EXISTENTE = 'Associação já existente',
  CONFIRMAR_PERSISTENCIA_PARA_INCLUSAO = 'Para realizar a inclusão de um novo nível é necessário salvar o nível atual. Deseja salvar o nível atual e realizar a inclusão de um novo nível?',
  NECESSARIO_PREENCHER_FILTRO = 'É necessário preencher pelo menos um dos filtros para realizar a pesquisa.',
  FILTRO_DATA_FINAL_INFERIOR = 'A Data do Evento Final não pode ser inferior que a Data do Evento Inicial.',
  A004 = 'Usuário não autorizado.',
  A002 = 'Usuário não possui permissões necessárias.',
  E003 = 'Recurso não identificado. \nVerifique o console para mais detalhes.',
  E004 = 'Erro interno. \nVerifique o console para mais detalhes',
  E005 = 'Erro não identificado. \nVerifique o console para mais detalhes.',
  E006 = 'Erro na requisição ao ENDCORP. Tente novamente mais tarde.',
  CONFIRMAR_EXCLUSAO_SISTEMA = 'Confirme a exclus&atilde;o do registro.<br>' +
                               '<span class="text-danger">Esta opera&ccedil;&atilde;o n&atilde;o poder&aacute; ser revertida.</span><br>' +
                               'Registros relacionados ao Sistema tamb&eacute;m ser&atilde;o exclu&iacute;dos.',
  CONFIRMAR_EXCLUSAO_TIPO_DEPARA = 'Confirme a exclus&atilde;o do registro.<br>' +
                               '<span class="text-danger">Esta opera&ccedil;&atilde;o n&atilde;o poder&aacute; ser revertida.</span><br>' +
                               'Registros relacionados ao Tipo de De/Para tamb&eacute;m <br>ser&atilde;o exclu&iacute;dos.',
VALIDACAO_NOTIFICACAO =  "Notificação é obrigatório",
}

export function formarMensagem(arrayMensagem: Array<string>, tipo: string) {

  switch (tipo) {

    case 'estruturas-estabelecimento':
      return `Já existe a associação da estrutura ${arrayMensagem[0]} ` +
        `do tipo estrutural ${arrayMensagem[1]} para o tipo de unidade.`;

    default:
      console.log('Opção não encontrada para formar mensagem.');
  }

}
