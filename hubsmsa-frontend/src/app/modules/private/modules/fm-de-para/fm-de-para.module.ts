import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultarDeParaComponent } from './components/consultar-de-para/consultar-de-para.component';
import { CadastrarEditarDeParaComponent } from './components/cadastrar-editar-de-para/cadastrar-editar-de-para.component';
import { FmDeParaRoutingModule } from './fm-de-para-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { TooltipModule } from 'ngx-bootstrap';
import { DeParaService } from './services/de-para.service';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';
import { VisualizarDeParaComponent } from './components/visualizar-de-para/visualizar-de-para.component';

@NgModule({
  declarations: [
    ConsultarDeParaComponent,
    CadastrarEditarDeParaComponent, VisualizarDeParaComponent
  ],
  imports: [
    CommonModule,
    FmDeParaRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ],
  providers: [
    DeParaService,
    SistemaService,
    EmpresaService,
    TiposDeParaService
  ]
})
export class FmDeParaModule { }
