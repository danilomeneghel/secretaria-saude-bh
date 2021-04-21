import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InputRadioListModule } from '@shared/modules/modules/input-radio-list';
import { PiwebBotoesModule } from '@shared/modules/modules/piweb-botoes';
import { PiwebGridModule } from '@shared/modules/modules/piweb-grid/piweb-grid.module';
import { PiwebTabelaModule } from '@shared/modules/modules/piweb-tabela';
import { PiwebUtilModule } from '@shared/modules/modules/piweb-util/piweb-util.module';
import { ModalModule } from 'ngx-bootstrap';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { SharedPipesModule } from '../shared-pipes/shared-pipes.module';
import { HeaderComponent } from './components/header/header.component';
import { HistoricoAlteracaoPadraoComponent } from './components/historico-alteracao-padrao/historico-alteracao-padrao.component';
import { PaginacaoComponent } from './components/paginacao/paginacao.component';
import { InputListaCheckboxComponent } from '@shared/components/components/input-lista-checkbox/input-lista-checkbox.component';
import { NgxPrintModule } from 'ngx-print';
import { PiwebInputsModule } from '@shared/modules/modules/piweb-inputs/piweb-inputs.module';
import { INgxSelectOptions, NgxSelectModule } from 'ngx-select-ex';
import { CollapseComponent } from '@shared/components/components/collapse/collapse.component';
import { CollapseGroupDirective } from '@shared/components/components/collapse-group/collapse-group.directive';

const customSelectOptions: INgxSelectOptions = { // Check the interface for more options
  optionValueField: 'id',
  optionTextField: 'name',
  keepSelectedItems: false
};

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
    FormsModule,
    ReactiveFormsModule,
    SharedPipesModule,
    PiwebBotoesModule,
    PiwebTabelaModule,
    PiwebGridModule,
    PiwebUtilModule,
    NgxPrintModule,
    PiwebInputsModule,
    NgxSelectModule.forRoot(customSelectOptions),
  ],
  declarations: [
    PaginacaoComponent,
    HistoricoAlteracaoPadraoComponent,
    HeaderComponent,
    InputListaCheckboxComponent,
    CollapseComponent,
    CollapseGroupDirective
  ],
  exports: [
    FormsModule,
    PaginacaoComponent,
    HistoricoAlteracaoPadraoComponent,
    PiwebBotoesModule,
    PiwebTabelaModule,
    PiwebGridModule,
    PiwebUtilModule,
    ReactiveFormsModule,
    InputRadioListModule,
    HeaderComponent,
    InputListaCheckboxComponent,
    NgxPrintModule,
    PiwebInputsModule,
    NgxSelectModule,
    CollapseComponent,
    CollapseGroupDirective
  ],
})
export class SharedComponentsModule {
}
