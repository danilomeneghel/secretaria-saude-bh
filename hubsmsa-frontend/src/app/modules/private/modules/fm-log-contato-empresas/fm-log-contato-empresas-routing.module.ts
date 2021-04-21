import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ConsultarLogContatoEmpresasComponent } from './components/consultar-log-contato-empresas/consultar-log-contato-empresas.component';
import { VisualizarLogContatoEmpresasComponent } from './components/visualizar-log-contato-empresas/visualizar-log-contato-empresas.component';

const routes: Routes = [{
  path: '',
  component: ConsultarLogContatoEmpresasComponent
},
{ data: {
  breadcrumb: [
    {
      titulo: 'Visualização'
    }
  ]
},
  path: 'visualizar/:id',
  component: VisualizarLogContatoEmpresasComponent,
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogContatosEmpresasRoutingModule { }
