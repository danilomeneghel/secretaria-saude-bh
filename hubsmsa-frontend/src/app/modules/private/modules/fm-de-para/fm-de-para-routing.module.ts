import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarDeParaComponent } from './components/consultar-de-para/consultar-de-para.component';
import { CadastrarEditarDeParaComponent } from './components/cadastrar-editar-de-para/cadastrar-editar-de-para.component';
import { VisualizarDeParaComponent } from './components/visualizar-de-para/visualizar-de-para.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarDeParaComponent
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
    component: CadastrarEditarDeParaComponent
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
    component: CadastrarEditarDeParaComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarDeParaComponent,
  }

]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmDeParaRoutingModule {
}
