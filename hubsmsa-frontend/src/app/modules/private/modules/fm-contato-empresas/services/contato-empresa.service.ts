import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { QueryParamsContatoEmpresaModel } from '../models/query-params-contato-empresa.model';
import { ContatoEmpresa } from '../models/contato-empresa.model';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';

const ROTAS = {
  empresas: '/contato-empresas',
  contatos: '/contatos',
  csv: '/csv',
  excel: '/excel'
}

@Injectable()
export class ContatoEmpresaService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/contato-empresas';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,

    router: Router
  ) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta: QueryParamsContatoEmpresaModel): Observable<PaginaResultadoPiweb<ContatoEmpresa>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<ContatoEmpresa>>(`${this.url}${ROTAS.empresas}${params}`).pipe(take(1));
  }

  recuperarDados(id: number): Observable<ContatoEmpresa> {
    return this.http.get<ContatoEmpresa>(`${this.url}${ROTAS.empresas}/${id}`).pipe(take(1));
  }

  cadastrarDados(dados: any): Observable<AppResponsePadraoModel> {
    return this.http.post<any>(`${this.url}${ROTAS.empresas}`, dados).pipe(take(1));
  }

  atualizarDados(id: number, dados: any): Observable<AppResponsePadraoModel> {
    return this.http.put<any>(`${this.url}${ROTAS.empresas}/${id}`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${this.url}${ROTAS.empresas}/${id}`).pipe(take(1));
  }

  consultarContatosDaEmpresa(idEmpresa: number): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.empresas}/${idEmpresa}${ROTAS.contatos}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsContatoEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.empresas}${ROTAS.csv}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      console.log(res);

      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    })
  }

  gerarExcel(_queryParamConsulta?: QueryParamsContatoEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.empresas}${ROTAS.excel}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      console.log(res);

      this.httpUtil.baixarArquivo(res, ArquivoEnum.EXCEL);
    }, error => {
      console.log(error);
    })
  }

}
