import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { TooltipModule } from 'ngx-bootstrap';

import { FmContatoEmpresasRoutingModule } from './fm-contato-empresas-routing.module';
import { CadastrarContatoEmpresasComponent } from './components/cadastrar-contato-empresas/cadastrar-contato-empresas.component';
import { ConsultarContatoEmpresasComponent } from './components/consultar-contato-empresas/consultar-contato-empresas.component';
import { VisualizarContatoEmpresasComponent } from './components/visualizar-contato-empresas/visualizar-contato-empresas.component';
import { ContatoEmpresaService } from './services/contato-empresa.service';

@NgModule({
  declarations: [
  CadastrarContatoEmpresasComponent,
  ConsultarContatoEmpresasComponent,
  VisualizarContatoEmpresasComponent],
  imports: [
    CommonModule,
    FmContatoEmpresasRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    ContatoEmpresaService
  ]
})
export class FmContatoEmpresasModule { }
