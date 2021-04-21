import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConsultarLogServicosComponent } from './components/consultar-log-servicos/consultar-log-servicos.component';

const routes: Routes = [{
  path: '',
  component: ConsultarLogServicosComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FmLogServicosRoutingModule { }
