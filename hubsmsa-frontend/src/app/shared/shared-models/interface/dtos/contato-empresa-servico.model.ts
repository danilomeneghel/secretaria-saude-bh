import { ContatoEmpresa } from 'app/modules/private/modules/fm-contato-empresas/models/contato-empresa.model';
import { Empresa } from './empresa.model';
import { Servico } from './servico.model';

export interface ContatoEmpresaServico {
  id: number;
  contato: ContatoEmpresa;
  empresa: Empresa;
  servico: Servico;
  notificacaoSucesso: boolean;
  notificacaoFalha: boolean;
}
