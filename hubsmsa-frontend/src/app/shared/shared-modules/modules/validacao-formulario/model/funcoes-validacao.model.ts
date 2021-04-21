import { AbstractControl } from '@angular/forms';
import { ValidacaoUtils } from './validacao-util-const.mode';

export const ValidacaoFormularioValidators = {
    validacaoEmail: (control: AbstractControl) => {
        if (/^([0-9a-z])\.{0,1}([a-z0-9-_]{1,}\.{0,1}){1,}[^.@@.$%*&#!*+_-](@){1}([^@.$%*&#!*'+_-]{1}[a-z0-9_-]*\.{1}){1,}[a-z0-9]{1,}[a-z0-9]$/.test(control.value)) {
            return null;
        }
        return { mEmail: true };
    },
    validacaoTelefone: (control: AbstractControl) => {
      if (control.value) {
        const telefoneValue = String(control.value).trim().replace(/\D/g, '');
        if (telefoneValue.length > 0) {
            if (!(/\d{11}/g.test(telefoneValue))) {
                return { phone: true };
            }
          }
        return null;
      }
    },
    validacaoCpfCnpj: (control: AbstractControl) => {
        if (typeof control.value!='undefined' && control.value) {
            if (ValidacaoUtils.validarCPf(control.value) || ValidacaoUtils.validarCnpj(control.value)) {
                return null;
            }
            else
            {
                return { cpfCnpj: true };
            }
        }
        return null;
    },
    validarTrim: (control: AbstractControl) => {
        if (control.value && String(control.value).trim() !== '') {
            return null;
        }
        return {
            required: true
        };
    },
    validarHoras: (control: AbstractControl) => {
        if (String(control.value).length > 0) {
            if (!(/^(1[0-2]|0?[1-9]):([0-5]?[0-9]):([0-5]?[0-9])(●?[AP]M)?$/.test(control.value))) {
                return { horas: true };
            }
        }
        return null;
    }
};
