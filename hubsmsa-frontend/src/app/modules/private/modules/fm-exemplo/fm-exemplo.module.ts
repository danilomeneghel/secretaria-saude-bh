import { PesquisarExemploComponent } from './components/pesquisar-exemplo/pesquisar-exemplo.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from '../../../../shared/shared-pipes/shared-pipes.module';
import { FmExemploRoutingModule } from './fm-exemplo-routing.module';
import { CrudExemploComponent } from './components/crud-exemplo/crud-exemplo.component';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';

@NgModule({
  declarations: [PesquisarExemploComponent, CrudExemploComponent],
  imports: [
    CommonModule,
    SharedComponentsModule,
    SharedPipesModule,
    FmExemploRoutingModule,
    SharedDirectivesModule
  ]
})
export class FmExemploModule { }
