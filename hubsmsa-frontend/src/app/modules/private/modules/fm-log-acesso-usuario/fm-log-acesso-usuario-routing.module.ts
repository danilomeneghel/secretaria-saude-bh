import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarLogAcessoUsuarioComponent } from './components/consultar-acesso-usuario/consultar-log-acesso-usuario.component';


const routes: Routes = [{
  path: '',
  component: ConsultarLogAcessoUsuarioComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogAcessoUsuarioRoutingModule { }
