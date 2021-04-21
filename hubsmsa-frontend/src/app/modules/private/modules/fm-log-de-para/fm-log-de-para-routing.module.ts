import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarLogDeParaComponent } from './components/consultar-log-de-para/consultar-log-de-para.component';
import { VisualizarLogDeParaComponent } from './components/visualizar-log-de-para/visualizar-log-de-para/visualizar-log-de-para.component';

const routes: Routes = [
  {
  path: '',
  component: ConsultarLogDeParaComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarLogDeParaComponent,
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogDeParaRoutingModule { }
