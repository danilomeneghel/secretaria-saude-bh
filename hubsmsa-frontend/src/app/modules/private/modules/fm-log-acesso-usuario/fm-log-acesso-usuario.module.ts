import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { FmLogAcessoUsuarioRoutingModule } from './fm-log-acesso-usuario-routing.module';
import { SharedPipesModule } from 'app/shared/shared-pipes/shared-pipes.module';
import { SharedDirectivesModule } from '@shared/directives/shared-directives.module';
import { TooltipModule } from 'ngx-bootstrap';
import { SharedServicesModule } from 'app/shared/shared-services/shared-services.module';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { ConsultarLogAcessoUsuarioComponent } from './components/consultar-acesso-usuario/consultar-log-acesso-usuario.component';

@NgModule({
  declarations: [ConsultarLogAcessoUsuarioComponent],
  imports: [
    CommonModule,
    FmLogAcessoUsuarioRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    SharedComponentsModule,
    SharedPipesModule,
    SharedDirectivesModule,
    SharedServicesModule.forRoot(),
    TooltipModule.forRoot()
  ]
})
export class FmLogAcessoUsuarioModule { }
