import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FmLogEmpresasRoutingModule } from './fm-log-empresas-routing.module';
import { ConsultarLogEmpresasComponent } from './components/consultar-log-empresas/consultar-log-empresas.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { VisualizarLogEmpresasComponent } from './components/visualizar-log-empresas/visualizar-log-empresas.component';

@NgModule({
  declarations: [
    ConsultarLogEmpresasComponent,
    VisualizarLogEmpresasComponent
  ],
  imports: [
    CommonModule,
    FmLogEmpresasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
  ]
})
export class FmLogEmpresasModule { }
