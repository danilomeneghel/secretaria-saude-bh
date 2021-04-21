import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { ConsultarLogContatoEmpresasComponent } from './components/consultar-log-contato-empresas/consultar-log-contato-empresas.component';
import { FmLogContatosEmpresasRoutingModule } from './fm-log-contato-empresas-routing.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { VisualizarLogContatoEmpresasComponent } from './components/visualizar-log-contato-empresas/visualizar-log-contato-empresas.component';

@NgModule({
  declarations: [ConsultarLogContatoEmpresasComponent, VisualizarLogContatoEmpresasComponent],
  imports: [
    CommonModule,
    FmLogContatosEmpresasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ]
})
export class FmLogContatoEmpresasModule { }
