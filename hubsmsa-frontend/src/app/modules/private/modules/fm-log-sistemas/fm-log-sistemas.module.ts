import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { TooltipModule } from 'ngx-bootstrap';
import { ConsultarLogSistemasComponent } from './components/consultar-log-sistemas/consultar-log-sistemas.component';
import { FmLogSistemasRoutingModule } from './fm-log-sistemas-routing.module';
import { SistemaService } from '../../../../shared/shared-services/sistema.service';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { VisualizarLogSistemasComponent } from './components/visualizar-log-sistemas/visualizar-log-sistemas.component';

@NgModule({
  declarations: [
    ConsultarLogSistemasComponent,
    VisualizarLogSistemasComponent
  ],
  imports: [
    CommonModule,
    FmLogSistemasRoutingModule,
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

export class FmLogSistemasModule { }
