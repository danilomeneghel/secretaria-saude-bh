import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ContadorMaxlengthDirective } from './directives/contador-digitos.directive';
import { ApenasNumeroDirective } from './directives/apenas-numero.directive';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';
import { IdentificadorPermissaoDirective } from './directives/identificador-permissao.directive';
import { VerificadorPermissaoDirective } from './directives/verificador-permissao.directive';

@NgModule({
    declarations: [ContadorMaxlengthDirective, ApenasNumeroDirective, KzmaskDirective, IdentificadorPermissaoDirective, VerificadorPermissaoDirective],
    imports: [
        CommonModule
    ],
  exports: [ContadorMaxlengthDirective, ApenasNumeroDirective, KzmaskDirective, IdentificadorPermissaoDirective, IdentificadorPermissaoDirective, IdentificadorPermissaoDirective, VerificadorPermissaoDirective]
})
export class SharedDirectivesModule { }
