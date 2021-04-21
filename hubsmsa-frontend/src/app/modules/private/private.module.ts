import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { PainelAdministrativoComponent } from './components/painel-administrativo/painel-administrativo.component';
import { PrivateRoutingModule } from './private-routing.module';

@NgModule({
    declarations: [
        PainelAdministrativoComponent
    ],
  imports: [
    CommonModule,
    PrivateRoutingModule,
    SharedComponentsModule
  ]
})
export class PrivateModule { }
