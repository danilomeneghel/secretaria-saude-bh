import { Injectable } from '@angular/core';
import { AbstractControl, FormArray, FormGroup } from '@angular/forms';
import { environment } from 'environments/environment';
import { MensagemValidacaoEnum } from '../model/mensagem.validacao.enum';
import { ValidacaoFormularioOption } from '../model/validacao-formulario-option.model';
import { Subject } from 'rxjs';
import { MensagensErro } from '@shared/modules/modules/validacao-formulario/model/mensagem-erro-const';

/**
 * Overwrite dos eventos de um FormControl, para atribuir a variavel nativeElements para obter a referencia do input utilizado.
 */

@Injectable()
export class ValidacaoFormularioService {
  private listarErros = new Subject<string[]>();
  $listarErros = this.listarErros.asObservable();

  constructor() {
  }

  /**
   * Metodo utilizado para varrer um formulário, encontrar os inputs que estão inválidos e disparar uma exceção informando uma mensagem
   * @param form Formumalario necessario para verificar as validações presentes no mesmo.
   * @param nomeDoGrupo Utilizado recursivamente para identificar o nome do grupo
   */
  validarFormulario(form: FormGroup | FormArray, option?: Partial<ValidacaoFormularioOption>, parentErrors?: Array<any>) {
    const errors = parentErrors || [];
    let elementoFocus: HTMLElement;
    let formArray;
    let mensagem = '';

    option = option || this.getOptionPadrao();

    if (form != null && form.invalid) {

      if (form.errors) {
        mensagem = this.definirMsgErro(form);

        if (option.multiplo) {
          errors.push(mensagem);
        } else {
          throw new Error(mensagem);
        }
      }

      Object.keys(form.controls).forEach(nomeDoControle => {
        const controle = form.get(nomeDoControle);

        if (controle instanceof FormGroup || controle instanceof FormArray) {
          if (controle instanceof FormArray) {
            option.grupoPai = nomeDoControle;
          }
          option.nomeDoGrupo = nomeDoControle;
          this.validarFormulario(controle, option, errors);
        }

        if (controle.invalid && !(controle instanceof FormGroup || controle instanceof FormArray)) {
          const controlIsFormArray = controle.parent.parent && controle.parent.parent instanceof FormArray;
          let elemento;
          let seletor;

          if (controlIsFormArray) {
            const formGroup = parseInt(option.nomeDoGrupo, 10);
            seletor = `*[formArrayName=${ option.grupoPai }]`;
            formArray = document.querySelector(seletor);
            const linhaDetalhe = formArray.querySelectorAll('piweb-linha-detalhe')[formGroup];

            elemento = linhaDetalhe.querySelector(`*[formControlName=${ nomeDoControle }`);
          } else {
            option.nomeDoGrupo = nomeDoControle;
            seletor = option.nomeDoGrupo != null ? `*[formGroupName=${ option.nomeDoGrupo }] *[formControlName=${ nomeDoControle }]`
              : `*[formControlName=${ nomeDoControle }]`;
            elemento = document.querySelector(seletor) as HTMLElement || document.querySelector(`*[id=${ nomeDoControle }]`) as HTMLElement;
          }

          if (elemento === null) {
            if (!environment.production) {
              console.error(MensagemValidacaoEnum.A006.replace('{VALUE}', nomeDoControle), `[${ nomeDoControle }]`);
            }
            return;
          }

          elementoFocus = this.definirFocoElemento(elementoFocus, option, elemento, formArray);
          const elementoLabel = elemento.getAttribute('name') || this.recuperarValorLabel(nomeDoControle);

          if (!elementoLabel && !environment.production) {
            console.error(MensagemValidacaoEnum.A008.replace('{VALUE}', nomeDoControle), `[${ nomeDoControle }]`);
          }

          if (controlIsFormArray && (!controle.value || controle.value.length === 0)) {
            mensagem = MensagemValidacaoEnum.A013.replace('{VALUE}', elementoLabel);
          } else {
            mensagem = this.definirMsgErro(controle, elemento, elementoLabel);
          }
          if (option.multiplo) {
            errors.push(mensagem);
          } else {
            throw new Error(mensagem);
          }
        }
      });
      if (errors.length > 0) {
        this.listarErros.next(errors);
      }
      if (!parentErrors) {
        throw new Error();
      }
    }

  }

  definirMsgErro(controle: AbstractControl, elemento?: any, textLabel?: string) {
    let mensagem = '';

    Object.keys(MensagensErro.mensagensErro).forEach(error => {
      if (controle.hasError(error)) {

        if (error === 'minlength') {
          const minLength = this.getMinLength(controle, elemento);
          mensagem = MensagensErro.mensagensErro[error](textLabel, minLength);
        } else if (typeof MensagensErro.mensagensErro[error] === 'function') {
          mensagem = MensagensErro.mensagensErro[error](textLabel);
        } else {
          mensagem = MensagensErro.mensagensErro[error];
        }
      }

    });

    if (!mensagem && !environment.production) {
      mensagem = MensagemValidacaoEnum.A007;
      console.log(MensagemValidacaoEnum.A010.replace('{VALUE}', JSON.stringify(controle.errors)), elemento);
    }

    return mensagem;
  }

  private getMinLength(controle, elemento) {
    const minValue = controle.errors.minlength.requiredLength;
    if (!minValue) {
      if (!environment.production) {
        console.log(MensagemValidacaoEnum.A009, elemento);
      }
      throw new Error(MensagemValidacaoEnum.E003);
    }
    return minValue;
  }

  private definirFocoElemento(elementoFocus: HTMLElement, option: Partial<ValidacaoFormularioOption>, elemento, formArray) {
    if (!elementoFocus && !option.elementoFocus) {
      const isFormArray = elemento.tagName.indexOf('-') > -1;
      elementoFocus = isFormArray ? elemento.querySelector('[tabindex]') : elemento;
      option.elementoFocus = elementoFocus;

      if (formArray) {
        formArray.querySelector('.collapse').classList.add('show');
      }

      // setTimeout porque quando o alerta é exibido num form grande o focus aparece errado
      setTimeout(() => elementoFocus.focus());
    }

    elemento.scrollIntoView({ behavior: 'smooth' });
    return elemento;
  }

  private recuperarValorLabel(nomeDoControle: string): string {
    const seletor = `*[for=${ nomeDoControle }]`;
    const elemento = document.querySelector(seletor) as HTMLElement;
    return elemento ? elemento.innerText : null;
  }

  private getOptionPadrao(): Partial<ValidacaoFormularioOption> {
    return { multiplo: true };
  }
}
