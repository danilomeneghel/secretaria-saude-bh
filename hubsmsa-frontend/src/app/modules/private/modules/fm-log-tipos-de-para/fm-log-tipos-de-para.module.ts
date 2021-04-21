import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FmLogTiposDeParaRoutingModule } from './fm-log-tipos-de-para-routing.module';
import { ConsultarLogTiposDeParaComponent } from './components/consultar-log-tipos-de-para/consultar-log-tipos-de-para.component';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { ReactiveFormsModule } from '@angular/forms';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { VisualizarLogTiposDeParaComponent } from './components/visualizar-log-tipos-de-para/visualizar-log-tipos-de-para.component';
import { LogTiposDeParaService } from './services/log-tipos-de-para.service';

@NgModule({
  declarations: [
    ConsultarLogTiposDeParaComponent,
    VisualizarLogTiposDeParaComponent,
  ],
  imports: [
    CommonModule,
    FmLogTiposDeParaRoutingModule,
    SharedComponentsModule,
    ReactiveFormsModule,
    TooltipModule.forRoot(),
    SharedPipesModule,
    SharedDirectivesModule
  ],
  providers: [
    LogTiposDeParaService
  ]
})
export class FmLogTiposDeParaModule { }
