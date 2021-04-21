import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FmTiposDeParaRoutingModule } from './fm-tipos-de-para-routing.module';
import { ConsultarTiposDeParaComponent } from './components/consultar-tipos-de-para/consultar-tipos-de-para.component';
import { CadastrarEditarTiposDeParaComponent } from './components/cadastrar-editar-tipos-de-para/cadastrar-editar-tipos-de-para.component';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { ReactiveFormsModule } from '@angular/forms';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { VisualizarTiposDeParaComponent } from './components/visualizar-tipos-de-para/visualizar-tipos-de-para.component';

@NgModule({
  declarations: [
    ConsultarTiposDeParaComponent, 
    CadastrarEditarTiposDeParaComponent, VisualizarTiposDeParaComponent
  ],
  imports: [
    CommonModule,
    FmTiposDeParaRoutingModule,
    SharedComponentsModule,
    ReactiveFormsModule,
    TooltipModule.forRoot(),
    SharedPipesModule,
    FmTiposDeParaRoutingModule,
    SharedDirectivesModule
  ],
  providers: [
    TiposDeParaService
  ]
})
export class FmTiposDeParaModule { }
