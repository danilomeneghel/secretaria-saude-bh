import { merge, Subscription } from 'rxjs';
import { AbstractControl, FormGroup } from '@angular/forms';
import { ValidatorsUtilService } from '@core/services/validators-util.service';
import { InputListaRadioModel } from '@shared/modules/modules/input-radio-list';
import { statusEnumMensagem } from '@shared/models/enum/status.enum';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';

export class AppUtilService {
  /**
   * @description Retorna o valor padrão dos radio buttons do tipo Status
   */
  static valoresPadraoInputRadioStatus() {
    return [
      {
        valor: 'A',
        valorExibicao: 'Ativo',
        default: true
      },
      {
        valor: 'I',
        valorExibicao: 'Inativo',
      }
    ];
  }

  static origemTipoPesquisa() {
    return [
      {
        valor: 'S',
        valorExibicao: 'HUBSMSA',
        default: true
      },
      {
        valor: 'A',
        valorExibicao: 'ARTERH',
      },
      {
        valor: 'Ambos',
        valorExibicao: 'Ambos',
      }
    ];
  }

  static origemTipo() {
    return [
      {
        valor: 'S',
        valorExibicao: 'HUBSMSA',
        default: true
      },
      {
        valor: 'A',
        valorExibicao: 'ARTERH',
      }
    ];
  }

  static valoresPadraoInputRadioBoolean() {
    return [
      {
        valor: 'S',
        valorExibicao: 'Sim'
      },
      {
        valor: 'N',
        valorExibicao: 'Não',
      }
    ];
  }

  static valoresPadraoInputRadioBooleanStatus() {
    return [
      {
        valor: true,
        valorExibicao: 'Sim'
      },
      {
        valor: false,
        valorExibicao: 'Não',
      }
    ];
  }

  static valoresPadraoInputRadioMotivoAlteracao() {
    return [
      {
        valor: 'M',
        valorExibicao: 'Mudança de descrição / Status',
      },
      {
        valor: 'E',
        valorExibicao: 'Erro'
      },
      {
        valor: 'O',
        valorExibicao: 'Outros'
      }
    ];
  }

  static valoresRadioListaAtendimento() {
    return [
      {
        valor: 'Ambulatorial',
        valorExibicao: 'Ambulatorial',
        default: true
      },
      {
        valor: 'Hospitalar',
        valorExibicao: 'Hospitalar',
      },
    ];
  }


  static listaTipoEstrutura() {
    return [
      {
        codigo: 1,
        valorExibicao: 'Selecione - AutoComplete',
        descricao: 'Administrativo',
      },
      {
        codigo: 2,
        valorExibicao: 'Primeiro Item',
      },
      {
        codigo: 3,
        valorExibicao: 'Segundo Item',
        descricao: 'Ambulatorial',
      },
      {
        codigo: 3,
        descricao: 'Hospitalar',
      },
    ];
  }

  static vinculoEorgInputRadio() {
    return [
      {
        valor: 'P',
        valorExibicao: 'Possui vínculo',
        default: true
      },
      {
        valor: 'N',
        valorExibicao: 'Não possui vínculo',
      },
      {
        valor: 'A',
        valorExibicao: 'Ambos'
      }
    ];
  }

  static igualarControls(formGroup: FormGroup, controls: string[]): Subscription[] {
    const subscriptions: Subscription[] = [];

    subscriptions.push(
      merge(formGroup.get(controls[0]).valueChanges, formGroup.get(controls[1]).valueChanges).subscribe(valor => {
        formGroup.patchValue({ [controls[0]]: valor, [controls[1]]: valor }, { emitEvent: false });
      })
    );

    return subscriptions;
  }

  static removeErrors(formControls: AbstractControl[], errors: string[]) {
    formControls.forEach(control => {
      errors.forEach(err => {
        if (control.hasError(err)) {
          control.setErrors(null);
        }
      });
    });
  }

  static arrayBufferToJson(data: ArrayBuffer) {
    if ('TextDecoder' in window) {
      // Decode as UTF-8
      const dataView = new DataView(data);
      const decoder = new TextDecoder('utf8');
      return JSON.parse(decoder.decode(dataView));
    } else {
      // Fallback decode as ASCII
      const decodedString = String.fromCharCode.apply(null, new Uint8Array(data));
      return JSON.parse(decodedString);
    }
  }

  static validarDataForm(dataIncialControl: AbstractControl, dataFinalControl: AbstractControl) {
    merge(
      dataIncialControl.valueChanges,
      dataFinalControl.valueChanges
    ).subscribe(() => {
      if (ValidatorsUtilService.intervaloData(dataIncialControl.value, dataFinalControl.value)) {
        dataFinalControl.setErrors({ intervaloDataInvalido: true });
      }
    });
  }

  static enumToInputRadioDataSource(enumObj: object): InputListaRadioModel[] {
    return Object.values(enumObj).map((enumValue): InputListaRadioModel => {
      return {
        valor: enumValue,
        valorExibicao: statusEnumMensagem[enumValue]
      };
    }
    );
  }

  static get dataAtual() {
    const dataAtual = new Date();
    const dia = AppUtilService.zeroEsquerda(dataAtual.getDate());
    const mes = AppUtilService.zeroEsquerda(dataAtual.getMonth() + 1);
    const ano = AppUtilService.zeroEsquerda(dataAtual.getFullYear());

    return `${dia}/${mes}/${ano}`;
  }

  static zeroEsquerda(valor: string | number) {
    return valor.toString().length === 1 ? '0' + valor.toString() : valor;
  }

  static removerMascaraTelefone(control: AbstractControl) {
    const valorSemDDD = control.value ? control.value.replace(/\(\d+\)/, '') : '';
    return KzmaskDirective.removerMascara(valorSemDDD);
  }
}
