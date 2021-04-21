import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { PiwebSelecionarArquivoComponent } from './components/piweb-selecionar-arquivo/piweb-selecionar-arquivo.component';
import { PiwebDataDirective } from './directives/piweb-data.directive';

@NgModule({
  declarations: [PiwebSelecionarArquivoComponent, PiwebDataDirective],
  imports: [CommonModule],
  exports: [PiwebSelecionarArquivoComponent, PiwebDataDirective],
  providers: [DatePipe]
})
export class PiwebInputsModule {
}
