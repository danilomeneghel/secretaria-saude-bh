import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { FmLogDeParaRoutingModule } from './fm-log-de-para-routing.module';
import { ConsultarLogDeParaComponent } from './components/consultar-log-de-para/consultar-log-de-para.component';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { LogDeParaService } from './services/log-de-para.service';
import { VisualizarLogDeParaComponent } from './components/visualizar-log-de-para/visualizar-log-de-para/visualizar-log-de-para.component';

@NgModule({
  declarations: [
    ConsultarLogDeParaComponent,
    VisualizarLogDeParaComponent,
    //VisualizarDeParaComponent
  ],
  imports: [
    CommonModule,
    FmLogDeParaRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    LogDeParaService
  ]
})
export class FmLogDeParaModule { }
