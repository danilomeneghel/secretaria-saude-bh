import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarLogSistemasComponent } from './components/consultar-log-sistemas/consultar-log-sistemas.component';
import { VisualizarLogSistemasComponent } from './components/visualizar-log-sistemas/visualizar-log-sistemas.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarLogSistemasComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarLogSistemasComponent,
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class FmLogSistemasRoutingModule { }
