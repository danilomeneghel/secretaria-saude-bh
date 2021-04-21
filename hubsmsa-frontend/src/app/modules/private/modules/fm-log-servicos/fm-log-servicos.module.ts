import { LogServicosService } from './services/log-servicos.service';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FmLogServicosRoutingModule } from './fm-log-servicos-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { TooltipModule } from 'ngx-bootstrap';
import { ConsultarLogServicosComponent } from './components/consultar-log-servicos/consultar-log-servicos.component';
import { ServicoService } from 'app/shared/shared-services/servico.service';

@NgModule({
  declarations: [ConsultarLogServicosComponent],
  imports: [
    CommonModule,
    FmLogServicosRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    LogServicosService,
    ServicoService,
  ]
})
export class FmLogServicosModule { }
