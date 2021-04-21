import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FmEmpresasRoutingModule } from './fm-empresas-routing.module';
import { CadastrarEmpresasComponent } from './components/cadastrar-empresas/cadastrar-empresas.component';
import { ConsultarEmpresasComponent } from './components/consultar-empresas/consultar-empresas.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { VisualizarEmpresasComponent } from './components/visualizar-empresas/visualizar-empresas.component';

@NgModule({
  declarations: [
    CadastrarEmpresasComponent,
    ConsultarEmpresasComponent,
    VisualizarEmpresasComponent
  ],
  imports: [
    CommonModule,
    FmEmpresasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    EmpresaService
  ]
})
export class FmEmpresasModule { }
