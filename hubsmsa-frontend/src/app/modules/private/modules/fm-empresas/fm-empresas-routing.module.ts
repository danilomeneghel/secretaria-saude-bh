import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastrarEmpresasComponent } from './components/cadastrar-empresas/cadastrar-empresas.component';
import { ConsultarEmpresasComponent } from './components/consultar-empresas/consultar-empresas.component';
import { VisualizarEmpresasComponent } from './components/visualizar-empresas/visualizar-empresas.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarEmpresasComponent
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
    component: CadastrarEmpresasComponent
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
    component: CadastrarEmpresasComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarEmpresasComponent,
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmEmpresasRoutingModule {
}
