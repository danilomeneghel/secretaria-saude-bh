import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarLogEmpresasComponent } from './components/consultar-log-empresas/consultar-log-empresas.component';
import { VisualizarLogEmpresasComponent } from './components/visualizar-log-empresas/visualizar-log-empresas.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarLogEmpresasComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarLogEmpresasComponent,
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogEmpresasRoutingModule {
}
