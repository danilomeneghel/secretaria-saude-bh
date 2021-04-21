import { CommonModule, DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { EnumTradutorPipe } from './pipes/enum-tradutor.pipe';
import { FormatTextPipe } from './pipes/format-text.pipe';
import { ListaFormatoPipe } from './pipes/lista-formato.pipe';
import { FormatarDataPipe } from './pipes/formatar-data.pipe';
import { SepararBarrasPipe } from './pipes/separar-barras.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    EnumTradutorPipe,
    FormatTextPipe,
    ListaFormatoPipe,
    FormatarDataPipe,
    SepararBarrasPipe
  ],
  exports: [
    EnumTradutorPipe,
    FormatTextPipe,
    ListaFormatoPipe,
    SepararBarrasPipe,
    FormatarDataPipe
  ],
  providers: [
    DatePipe
  ]
})
export class SharedPipesModule {
}
