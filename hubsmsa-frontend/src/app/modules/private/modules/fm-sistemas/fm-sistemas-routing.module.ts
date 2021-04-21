import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConsultarSistemasComponent } from './components/consultar-sistemas/consultar-sistemas.component';
import { CadastrarSistemasComponent } from './components/cadastrar-sistemas/cadastrar-sistemas.component';
import { VisualizarSistemasComponent } from './components/visualizar-sistemas/visualizar-sistemas.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarSistemasComponent
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
    component: CadastrarSistemasComponent
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
    component: CadastrarSistemasComponent
  },
  { data: {
    breadcrumb: [
      {
        titulo: 'Visualização'
      }
    ]
  },
    path: 'visualizar/:id',
    component: VisualizarSistemasComponent,
  }

]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class FmSistemasRoutingModule { }
