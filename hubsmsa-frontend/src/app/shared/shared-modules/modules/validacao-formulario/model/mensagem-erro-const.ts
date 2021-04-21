import { MensagemValidacaoEnum } from '@shared/modules/modules/validacao-formulario/model/mensagem.validacao.enum';

export class MensagensErro {
  public static get mensagensErro() {
    return {
      required: textLabel => MensagemValidacaoEnum.A003.replace('{VALUE}', textLabel),
      arquivoInvalido: textLabel => MensagemValidacaoEnum.A016.replace('{VALUE}', textLabel),
      minlength: (textLabel, minValue) => MensagemValidacaoEnum.A005.replace('{VALUE}', textLabel).replace('{MIN_VALUE}', minValue),
      pattern: textLabel => MensagemValidacaoEnum.A004.replace('{VALUE}', textLabel),
      email: textLabel => MensagemValidacaoEnum.A004.replace('{VALUE}', textLabel),
      CpfCnpj: textLabel => MensagemValidacaoEnum.A004.replace('{VALUE}', textLabel),
      fileDateMaxCurrent: MensagemValidacaoEnum.A014,
      invalidDate: MensagemValidacaoEnum.A015,
      listaEmailsInvalidos: textLabel => MensagemValidacaoEnum.A017.replace('{VALUE}', textLabel),
      numeroInvalido: textLabel => MensagemValidacaoEnum.A018.replace('{VALUE}', textLabel),
      enderecoInternetInvalido: textLabel => MensagemValidacaoEnum.A019.replace('{VALUE}', textLabel),
      acompanhamentoInvalido: MensagemValidacaoEnum.A020,
      cpfInvalido: MensagemValidacaoEnum.A012,
      intervaloDataInvalido: MensagemValidacaoEnum.A021,
      segundoNomeInvalido: MensagemValidacaoEnum.A022,
      nomeDescricaoMinLength: MensagemValidacaoEnum.A023,
      campoObrigatorioPesquisa: MensagemValidacaoEnum.A024,
      horaInvalida: MensagemValidacaoEnum.A025,
      codigoMinLength: MensagemValidacaoEnum.A026,
      cnesMinLength: textLabel => MensagemValidacaoEnum.A027.replace('{VALUE}', textLabel),
      emailInvalido: textLabel => MensagemValidacaoEnum.A027.replace('{VALUE}', textLabel),
      minAtividade: textLabel => MensagemValidacaoEnum.A028.replace('{VALUE}', textLabel),
      sequenciaNumerica: textLabel => MensagemValidacaoEnum.A029.replace('{VALUE}', textLabel),
      codigoEndcorpRequired: MensagemValidacaoEnum.A030,
    };
  }
}
