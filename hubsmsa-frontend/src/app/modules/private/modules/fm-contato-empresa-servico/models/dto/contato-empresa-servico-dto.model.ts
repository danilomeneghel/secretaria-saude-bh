import { Empresa } from "@shared/models/interface/dtos/empresa.model";
import { Servico } from "@shared/models/interface/dtos/servico.model";
import { ContatoEmpresa } from "../../../fm-contato-empresas/models/contato-empresa.model";

export class ContatoEmpresaServicoDTO {
  id: number;
  contato: ContatoEmpresa;
  empresa: Empresa;
  servico: Servico;
  notificacaoSucesso: string;
  notificacaoFalha: string;
  notificacao: string;
}
