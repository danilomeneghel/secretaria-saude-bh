import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarTiposDeParaComponent } from './components/consultar-tipos-de-para/consultar-tipos-de-para.component';
import { CadastrarEditarTiposDeParaComponent } from './components/cadastrar-editar-tipos-de-para/cadastrar-editar-tipos-de-para.component';
import { VisualizarTiposDeParaComponent } from './components/visualizar-tipos-de-para/visualizar-tipos-de-para.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarTiposDeParaComponent,
  },
  {
    data: {
      breadcrumb: [
        {
          titulo: 'Cadastramento'
        }
      ]
    },
    path: 'cadastrar',
    component: CadastrarEditarTiposDeParaComponent,
  },
  {
    data: {
      breadcrumb: [
        {
          titulo: 'Edição'
        }
      ]
    },
    path: 'editar/:id',
    component: CadastrarEditarTiposDeParaComponent,
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarTiposDeParaComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmTiposDeParaRoutingModule { }
