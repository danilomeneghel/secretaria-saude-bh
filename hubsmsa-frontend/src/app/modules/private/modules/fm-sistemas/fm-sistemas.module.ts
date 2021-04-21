import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { TooltipModule } from 'ngx-bootstrap';
import { CadastrarSistemasComponent } from './components/cadastrar-sistemas/cadastrar-sistemas.component';
import { ConsultarSistemasComponent } from './components/consultar-sistemas/consultar-sistemas.component';
import { FmSistemasRoutingModule } from './fm-sistemas-routing.module';
import { SistemaService } from '../../../../shared/shared-services/sistema.service';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { VisualizarSistemasComponent } from './components/visualizar-sistemas/visualizar-sistemas.component';

@NgModule({
  declarations: [
    CadastrarSistemasComponent, ConsultarSistemasComponent, VisualizarSistemasComponent
  ],
  imports: [
    CommonModule,
    FmSistemasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    SistemaService
  ]
})

export class FmSistemasModule { }
