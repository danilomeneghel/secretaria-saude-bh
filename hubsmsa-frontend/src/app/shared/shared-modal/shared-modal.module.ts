import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalConfirmComponent } from './modal/modal-confirm/modal-confirm.component';
import { PiwebTabelaModule } from '@shared/modules/modules/piweb-tabela';
import { PiwebUtilModule } from '@shared/modules/modules/piweb-util';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PiwebGridModule } from '@shared/modules/modules/piweb-grid';
import { PiwebBotoesModule } from '@shared/modules/modules/piweb-botoes';
import { SharedPipesModule } from '../shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedComponentsModule } from '@shared/components/shared-components.module';

@NgModule({
  declarations: [
    ModalConfirmComponent
  ],
  exports: [
    ModalConfirmComponent    
  ],
  entryComponents: [
    ModalConfirmComponent    
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PiwebTabelaModule,
    PiwebUtilModule,
    PiwebGridModule,
    PiwebBotoesModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedComponentsModule
  ]
})
export class SharedModalModule { }
