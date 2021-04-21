import { AbstractControl, FormGroup } from '@angular/forms';

export class ValidatorsUtilService {

  public static extensaoArquivo(extensao: string) {
    return (control: AbstractControl): { [key: string]: any | null } => {
      const nomeArquivo = control.value;
      const nomeArquivoDividido: string[] = nomeArquivo ? nomeArquivo.split('.') : '';
      const extensaoArquivoPos = nomeArquivoDividido.length - 1;

      return nomeArquivoDividido[extensaoArquivoPos] !== extensao ? { arquivoInvalido: true } : null;
    };
  }

  public static existenciaArquivo(control: AbstractControl): { [key: string]: any | null } {
    return !control.value ? { required: true } : null;
  }

  public static listaEmails(control: AbstractControl): { [key: string]: any | null } {
    const emails = control.value;
    const listaEmails: string[] = emails ? emails.split(',').map(email => email.trim()) : [];
    const emailFormatter = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    const response = listaEmails.every(value => emailFormatter.test(value));
    return response === false ? { listaEmailsInvalidos: true } : null;
  }

  public static apenasNumero(control: AbstractControl): { [key: string]: any | null } {
    return isNaN(control.value) === true ? { numeroInvalido: true } : null;
  }

  public static enderecoInternet(control: AbstractControl): { [key: string]: any | null } {
    const endereco: string = control.value;
    const urlValidator = /^(http:\/\/|https:\/\/)?[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/[^\s]*)?$/;
    return !urlValidator.test(endereco) ? { enderecoInternetInvalido: true } : null;
  }

  public static validarSegundoNome(control: AbstractControl): { [key: string]: any | null } {
    const nomeValidator = /^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$/;
    const nome = control.value;
    if (!nome) {
      return null;
    }
    const nomeArray: string[] = nome ? nome.split(' ') : '';
    const segundoNome: string = nomeArray[1] || '';
    return !nomeValidator.test(segundoNome) ? { segundoNomeInvalido: true } : null;
  }

  /**
   * @description Verifica se data inicial eh menor que final e aplica erro se for
   * @param dataIncialBr Data inicial no formato pt-br a ser comparada
   * @param dataFinalBr Data final no formato pt-br a ser comparada
   */
  public static intervaloData(dataIncialBr: string, dataFinalBr: string): boolean {
    const dataInicialArr = dataIncialBr ? dataIncialBr.split('/').map(item => Number(item)) : [];
    const dataFinalArr = dataFinalBr ? dataFinalBr.split('/').map(item => Number(item)) : [];
    const dataInicial = new Date(dataInicialArr[2], dataInicialArr[1] - 1, dataInicialArr[0]);
    const dataFinal = new Date(dataFinalArr[2], dataFinalArr[1] - 1, dataFinalArr[0]);

    return dataInicial > dataFinal;
  }

  public static primeiroNomeMinLength(control: AbstractControl): { [key: string]: any | null } {
    const primeiroNome = control && control.value ? control.value.split(' ')[0] : '';
    return primeiroNome.length >= 3 || primeiroNome.length === 0 ? null : { nomeDescricaoMinLength: true };
  }

  public static cpf(control: AbstractControl): { [key: string]: any | null } {
    let cpf = control.value;
    if (!cpf) {
      return null;
    }
    if (cpf.length === 14) {
      cpf = cpf.replace(/\D+/g, '');

      if (cpf.length < 11) {
        return { cpfInvalido: true };
      }
      let soma;
      let resto;
      soma = 0;

      for (let i = 1; i <= 9; i++) {
        soma = soma + parseInt(cpf.substring(i - 1, i), 0) * (11 - i);
      }
      resto = (soma * 10) % 11;

      if ((resto === 10) || (resto === 11)) {
        resto = 0;
      }
      if (resto !== parseInt(cpf.substring(9, 10), 0)) {
        return { cpfInvalido: true };
      }

      soma = 0;
      for (let i = 1; i <= 10; i++) {
        soma = soma + parseInt(cpf.substring(i - 1, i), 0) * (12 - i);
      }
      resto = (soma * 10) % 11;

      if ((resto === 10) || (resto === 11)) {
        resto = 0;
      }
      if (resto !== parseInt(cpf.substring(10, 11), 0)) {
        return { cpfInvalido: true };
      }
      return null;
    } else {
      return { cpfInvalido: true };
    }
  }

  public static minimoUmFiltro(form: FormGroup) {
    return Object.values(form.value).every(key => !key) ? { campoObrigatorioPesquisa: true } : null;
  }

  public static horaInvalida(control: AbstractControl): { [key: string]: any | null } {
    return control.value && !(/^([0-2][0-3]):([0-5][0-9]):([0-5][0-9])/).test(control.value) ? { horaInvalida: true } : null;
  }

  public static cnsValido(cnsControl: AbstractControl) {
    const cns = cnsControl.value || '';

    let isValid = false;

    if (cns.match('[1-2]\\d{10}00[0-1]\\d') || cns.match('[7-9]\\d{14}')) {
      isValid = ValidatorsUtilService.somaPonderada(cns, 15) % 11 === 0;
    }

    return isValid || !cns ? null : { pattern: true };
  }

  public static somaPonderada(number: string | number, peso: number) {
    const numArray = number.toString().split('').map(num => Number(num));
    let soma = 0;
    for (let i = 0; i < numArray.length; i++) {
      soma += Number(numArray[i] * (peso - i));
    }
    return soma;
  }

  public static cnesMinLength(control: AbstractControl): { [key: string]: any | null } {
    const cnesLength = control && control.value ? control.value.split(' ')[0] : '';
    return cnesLength.length === 7 || cnesLength.length === 0 ? null : { cnesMinLength: true };
  }

  public static email(control: AbstractControl): { [key: string]: any | null } {
    const emailFormatter: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return emailFormatter.test(control.value) || control.value === '' || control.value === null ? null : { emailInvalido: true };
  }

  public static verificarSequenciaDeZero(control: AbstractControl): { [key: string]: any | null } {
    const verificador: RegExp = /^(0){2,}(?!\d)/g;
    return verificador.test(control.value) ? { pattern: true } : null;
  }

  public static sequenciaNumerica(max: number, callback: any): { [key: string]: any | null } {
    return (control: AbstractControl) => {
      const valor = callback(control);
      const verificador: RegExp = new RegExp(`([0-9])\\1{${ max }}`);
      return verificador.test(valor) ? { sequenciaNumerica: true } : null;
    };
  }

}

