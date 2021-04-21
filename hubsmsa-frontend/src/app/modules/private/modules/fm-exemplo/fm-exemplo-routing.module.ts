import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PesquisarExemploComponent } from './components/pesquisar-exemplo/pesquisar-exemplo.component';
import { CrudExemploComponent } from './components/crud-exemplo/crud-exemplo.component';

const routes: Routes = [
    {
        path: '',
        component: PesquisarExemploComponent,
    },
    {
        path: 'cadastrar',
        component: CrudExemploComponent,
    },
    {
        path: 'editar/:id',
        component: CrudExemploComponent,
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class FmExemploRoutingModule {
}
