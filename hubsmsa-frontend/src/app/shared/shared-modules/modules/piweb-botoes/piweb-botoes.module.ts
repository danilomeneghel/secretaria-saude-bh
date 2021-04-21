import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PiwebBotaoPrimarioComponent, PiwebBotaoSecundarioComponent } from './components/index';
import { PiwebBtnPrimarioDirective } from './directives/piweb-btn-primario.directive';
import { PiwebBtnSecundarioDirective } from './directives/piweb-btn-secundario.directive';
import { PiwebBtnVoltarDirective } from "@shared/modules/modules/piweb-botoes/directives/piweb-btn-voltar.directive";
import { PiwebBtnIconeDirective } from '@shared/modules/modules/piweb-botoes/directives/piweb-btn-icone.directive';
import { PiwebBtnEditarDirective } from '@shared/modules/modules/piweb-botoes/directives/piweb-btn-editar.directive';
import { PiwebBtnRemoverDirective } from '@shared/modules/modules/piweb-botoes/directives/piweb-btn-remover.directive';

@NgModule({
  declarations: [
    PiwebBotaoPrimarioComponent,
    PiwebBotaoSecundarioComponent,
    PiwebBtnPrimarioDirective,
    PiwebBtnSecundarioDirective,
    PiwebBtnVoltarDirective,
    PiwebBtnIconeDirective,
    PiwebBtnEditarDirective,
    PiwebBtnRemoverDirective
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    PiwebBotaoPrimarioComponent,
    PiwebBotaoSecundarioComponent,
    PiwebBtnSecundarioDirective,
    PiwebBtnPrimarioDirective,
    PiwebBtnVoltarDirective,
    PiwebBtnIconeDirective,
    PiwebBtnEditarDirective,
    PiwebBtnRemoverDirective
  ]
})
export class PiwebBotoesModule {
}
