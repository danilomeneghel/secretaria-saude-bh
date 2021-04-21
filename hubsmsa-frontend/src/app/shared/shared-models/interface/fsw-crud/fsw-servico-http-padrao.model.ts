import { Observable } from 'rxjs';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';

export interface FswServicoHttpPadraoModel {
  tempFiltroPesquisa: any;
  moduleParentRouter: string;

  pesquisarDados(queryParamConsulta?: any): Observable<PaginaResultadoPiweb<any>>;

  cadastrarDados(cadastrarModeloDto: any): Observable<AppResponsePadraoModel>;

  atualizarDados(id: number, atualizarModeloDto: any): Observable<any>;

  recuperarDados(id: number, queryParams?: any): Observable<any>;

  removerDados(id: number): Observable<any>;
}
