import { CadastrarEmpresaServicoComponent } from './components/cadastrar-contato-empresa-servico/cadastrar-empresa-servico/cadastrar-empresa-servico.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarContatoEmpresaServicoComponent } from './components/consultar-contato-empresa-servico/consultar-contato-empresa-servico.component';

const routes: Routes = [
  {
    path: '',
    component: ConsultarContatoEmpresaServicoComponent
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
    component: CadastrarEmpresaServicoComponent
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
    component: CadastrarEmpresaServicoComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmContatoEmpresaServicoRoutingModule { }
