import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarLogTiposDeParaComponent } from './components/consultar-log-tipos-de-para/consultar-log-tipos-de-para.component';
import { VisualizarLogTiposDeParaComponent } from './components/visualizar-log-tipos-de-para/visualizar-log-tipos-de-para.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarLogTiposDeParaComponent,
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarLogTiposDeParaComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogTiposDeParaRoutingModule { }
