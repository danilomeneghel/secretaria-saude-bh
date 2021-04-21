import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { PaginationModule } from 'ngx-bootstrap';
import { PiwebTabelaHeaderComponent } from './components/piweb-tabela-header/piweb-tabela-header.component';
import { PiwebTabelaSimplesComponent } from './components/piweb-tabela-simples';
import { PiwebTabelaComponent } from './components/piweb-tabela/piweb-tabela.component';
import { PiwebColunaDirective } from './directives/piweb-coluna.directive';
import { PiwebTabelaBotaoDirective } from './directives/piweb-tabela-botao.directive';
import { PiwebUtilService } from './services/piweb-utils.service';
@NgModule({
  declarations: [
    PiwebTabelaSimplesComponent,
    PiwebTabelaComponent,
    PiwebTabelaHeaderComponent,
    PiwebColunaDirective,
    PiwebTabelaBotaoDirective
  ],
  exports: [
    PiwebTabelaSimplesComponent,
    PiwebTabelaComponent,
    PiwebTabelaHeaderComponent,
    PiwebColunaDirective,
    PiwebTabelaBotaoDirective
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    PaginationModule.forRoot(),
    RouterModule
  ],
  providers: [PiwebUtilService]
})
export class PiwebTabelaModule { }
