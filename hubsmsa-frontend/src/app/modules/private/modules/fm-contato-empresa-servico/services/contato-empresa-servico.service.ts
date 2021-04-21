import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { Observable } from 'rxjs';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { take } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { QueryParamsContatoServico } from '../models/query-params.model';
import { HttpUtilService } from '@core/services/http-util.service';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { ContatoEmpresaServicoDTO } from '../models/dto/contato-empresa-servico-dto.model';
import { ContatoEmpresaServico } from '@shared/models/interface/dtos/contato-empresa-servico.model';

const ROTAS = {
  contatoEmpresaServico: '/contato-empresa-servico',
  pesquisar: '/pesquisar',
  visualizar: '/visualizar',
  csv: '/csv',
  excel: '/excel'
}
@Injectable({
  providedIn: 'root'
})
export class ContatoEmpresaServicoService extends FswHttpService {
  readonly url = environment.apiUrl;
  tempFiltroPesquisa: FormGroup;
  moduleParentRouter = '/admin/contato-empresa-servico';

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta: QueryParamsContatoServico): Observable<PaginaResultadoPiweb<ContatoEmpresaServicoDTO>> {
     const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<ContatoEmpresaServicoDTO>>(`${this.url}${ROTAS.contatoEmpresaServico}${params}`).pipe(take(1));
  }

  recuperarDados(id: number): Observable<ContatoEmpresaServico> {
    return this.http.get<ContatoEmpresaServico>(`${this.url}${ROTAS.contatoEmpresaServico}/${id}`).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${this.url}${ROTAS.contatoEmpresaServico}/${id}`).pipe(take(1));
  }

  cadastrarDados(dados: any): Observable<AppResponsePadraoModel> {
    return this.http.post<any>(`${this.url}${ROTAS.contatoEmpresaServico}`, dados).pipe(take(1));
  }

  atualizarDados(id: number, dados: any): Observable<AppResponsePadraoModel> {
    return this.http.put<any>(`${this.url}${ROTAS.contatoEmpresaServico}/${id}`, dados).pipe(take(1));
  }


}
