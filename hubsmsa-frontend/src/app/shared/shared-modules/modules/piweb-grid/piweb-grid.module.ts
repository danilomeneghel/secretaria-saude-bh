import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { PiwebUtilModule } from '../piweb-util/piweb-util.module';
import { PiwebBarraAcoesComponent } from '@shared/modules/modules/piweb-grid/components';
import { PiwebBodyComponent } from './components/piweb-body/piweb-body.component';
import { PiwebContainerPadraoComponent } from '@shared/modules/modules/piweb-grid/components';
import { PiwebLinhaDetalheComponent } from './components/piweb-linha-detalhe/piweb-linha-detalhe.component';
import { PiwebMestreDetalheComponent } from '@shared/modules/modules/piweb-grid/components';

@NgModule({
  declarations: [
    PiwebContainerPadraoComponent,
    PiwebBarraAcoesComponent,
    PiwebBodyComponent,
    PiwebMestreDetalheComponent,
    PiwebLinhaDetalheComponent
  ],
  imports: [
    CommonModule,
    PiwebUtilModule
  ],
  exports: [
    PiwebContainerPadraoComponent,
    PiwebBarraAcoesComponent,
    PiwebBodyComponent,
    PiwebMestreDetalheComponent,
    PiwebLinhaDetalheComponent
  ]
})
export class PiwebGridModule {
}
