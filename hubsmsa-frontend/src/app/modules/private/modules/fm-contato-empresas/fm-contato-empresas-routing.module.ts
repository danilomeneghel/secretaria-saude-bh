import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CadastrarContatoEmpresasComponent } from './components/cadastrar-contato-empresas/cadastrar-contato-empresas.component';
import { ConsultarContatoEmpresasComponent } from './components/consultar-contato-empresas/consultar-contato-empresas.component';
import { VisualizarContatoEmpresasComponent } from './components/visualizar-contato-empresas/visualizar-contato-empresas.component';



const routes: Routes = [
  {
    path: '',
    component: ConsultarContatoEmpresasComponent
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
    component: CadastrarContatoEmpresasComponent
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
    component: CadastrarContatoEmpresasComponent
  },
  {
    data: {
      breadcrumb: [
        {
          titulo: 'Visualização'
        }
      ]
    },
    path: 'visualizar/:id',
    component: VisualizarContatoEmpresasComponent,
  }

]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmContatoEmpresasRoutingModule {
}
