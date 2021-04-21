import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { ContatoEmpresaService } from '../fm-contato-empresas/services/contato-empresa.service';
import { ServicoService } from 'app/shared/shared-services/servico.service';
import { FmContatoEmpresaServicoRoutingModule } from './fm-contato-empresa-servico-routing.module';
import { ContatoEmpresaServicoService } from './services/contato-empresa-servico.service';
import { CadastrarEmpresaServicoComponent } from './components/cadastrar-contato-empresa-servico/cadastrar-empresa-servico/cadastrar-empresa-servico.component';
import { ConsultarContatoEmpresaServicoComponent } from './components/consultar-contato-empresa-servico/consultar-contato-empresa-servico.component';

@NgModule({
  declarations: [ConsultarContatoEmpresaServicoComponent, CadastrarEmpresaServicoComponent],
  imports: [
    CommonModule,
    FmContatoEmpresaServicoRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    ContatoEmpresaServicoService,
    SistemaService,
    EmpresaService,
    ContatoEmpresaService,
    ServicoService
  ]
})
export class FmContatoEmpresaServicoModule { }
