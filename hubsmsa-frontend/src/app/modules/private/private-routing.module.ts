import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { permissoesRotas } from '@shared/models/class/permissoes-rotas';
import { PainelAdministrativoComponent } from './components/painel-administrativo/painel-administrativo.component';
import { ModuleAuthGuard } from './guards/module-auth-guard.service';
import { PrivateGuard } from './guards/private-guard';

export const routes: Routes = [
  {
    path: 'admin',
    component: PainelAdministrativoComponent,
    data: {
      titulo: 'Início',
      ativarLink: true
    },

    canActivate: [PrivateGuard],
    children: [
      {
        path: 'exemplo',
        loadChildren: './modules/fm-exemplo/fm-exemplo.module#FmExemploModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastro'
            },
            {
              titulo: 'Exemplo'
            },
          ],
          permissoesNecessarias: permissoesRotas.exemplo,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'empresas',
        loadChildren: './modules/fm-empresas/fm-empresas.module#FmEmpresasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastros'
            },
            {
              titulo: 'Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.empresas,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'contato-empresas',
        loadChildren: './modules/fm-contato-empresas/fm-contato-empresa.module#FmContatoEmpresasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastros'
            },
            {
              titulo: 'Contatos das Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.contatoDeEmpresas,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'sistemas',
        loadChildren: './modules/fm-sistemas/fm-sistemas.module#FmSistemasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastros'
            },
            {
              titulo: 'Sistemas das Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.sistemas,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'log-sistemas',
        loadChildren: './modules/fm-log-sistemas/fm-log-sistemas.module#FmLogSistemasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Sistemas das Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.sistemas,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'tipos-de-para',
        loadChildren: './modules/fm-tipos-de-para/fm-tipos-de-para.module#FmTiposDeParaModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastro'
            },
            {
              titulo: 'Tipos de De/Para'
            },
          ],
          permissoesNecessarias: permissoesRotas.tiposDeDePara,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'de-para',
        loadChildren: './modules/fm-de-para/fm-de-para.module#FmDeParaModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastro'
            },
            {
              titulo: 'De/Para'
            },
          ],
          permissoesNecessarias: permissoesRotas.dePara,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'contato-empresa-servico',
        loadChildren: './modules/fm-contato-empresa-servico/fm-contato-empresa-servico.module#FmContatoEmpresaServicoModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Cadastro'
            },
            {
              titulo: 'Associação de Contatos da Empresa a Notificação de Serviços'
            },
          ],
          permissoesNecessarias: permissoesRotas.exemplo,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'log-empresas',
        loadChildren: './modules/fm-log-empresas/fm-log-empresas.module#FmLogEmpresasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.empresas,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'log-contato-empresas',
        loadChildren: './modules/fm-log-contato-empresas/fm-log-contato-empresas.module#FmLogContatoEmpresasModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Contatos das Empresas'
            }
          ],
          permissoesNecessarias: permissoesRotas.empresas,

          path: 'log-tipo-de-para',
          loadChildren: './modules/fm-log-tipos-de-para/fm-log-tipos-de-para.module#FmLogTiposDeParaModule',
          data: {
            breadcrumb: [
              {
                titulo: 'Log'
              },
              {
                titulo: 'Tipos de De/Para'
              },
            ],
            permissoesNecessarias: permissoesRotas.tiposDeDePara,
          },
          canActivate: [ModuleAuthGuard]
        }

      },
      {
        path: 'log-tipo-de-para',
        loadChildren: './modules/fm-log-tipos-de-para/fm-log-tipos-de-para.module#FmLogTiposDeParaModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Tipos de De/Para'
            },
          ],
          permissoesNecessarias: permissoesRotas.tiposDeDePara,
        },
        canActivate: [ModuleAuthGuard]
      },
      {
        path: 'log-de-para',
        loadChildren: './modules/fm-log-de-para/fm-log-de-para.module#FmLogDeParaModule',
        data: {
           breadcrumb: [
            {
              titulo: 'Log'
            },
            {
              titulo: 'De/Para'
            },
          ],
          permissoesNecessarias: permissoesRotas.dePara,
        },
        canActivate: [ModuleAuthGuard]

      },
      {
        path: 'log-acesso-usuario',
        loadChildren: './modules/fm-log-acesso-usuario/fm-log-acesso-usuario.module#FmLogAcessoUsuarioModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Acessos de Usuários'
            },
          ],
          permissoesNecessarias: permissoesRotas.tiposDeDePara,
        },
        canActivate: [ModuleAuthGuard]

      },
      {
        path: 'log-de-servicos',
        loadChildren: './modules/fm-log-servicos/fm-log-servicos.module#FmLogServicosModule',
        data: {
          breadcrumb: [
            {
              titulo: 'Logs'
            },
            {
              titulo: 'Processamento de Serviços'
            },
          ],
          permissoesNecessarias: permissoesRotas.exemplo,
        },
        canActivate: [ModuleAuthGuard]

      }
    ],
  }

];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrivateRoutingModule {
}
