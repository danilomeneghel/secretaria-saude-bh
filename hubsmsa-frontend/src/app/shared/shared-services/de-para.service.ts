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
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { DePara } from '../shared-models/interface/dtos/de-para';
import { LogDePara } from '@shared/models/interface/dtos/log-de-para';
import { QueryParamsLogDePara } from '../../modules/private/modules/fm-log-de-para/models/query-params-log-de-para.model';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';

const ROTAS = {
  dePara: '/de-para',
  selecao: '/selecao',
  csv: '/csv',
  excel: '/excel'
}

@Injectable()
export class DeParaService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/de-para';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,

    router: Router
  ) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta: Partial<QueryParamsLogDePara>): Observable<PaginaResultadoPiweb<DePara>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<DePara>>(`${this.url}${ROTAS.dePara}${params}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsLogDePara) {
    this.http.get(`${this.url}${ROTAS.dePara}${ROTAS.csv}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    })
  }

  gerarExcel(_queryParamConsulta?: QueryParamsLogDePara) {
    this.http.get(`${this.url}${ROTAS.dePara}${ROTAS.excel}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.EXCEL);
    }, error => {
      console.log(error);
    })
  }

  recuperarDados(id: number): Observable<DePara> {
    return this.http.get<DePara>(`${this.url}${ROTAS.dePara}/${id}`).pipe(take(1));
  }
  
  recuperarDadosLog(id: number): Observable<LogDePara> {
    return this.http.get<LogDePara>(`${this.url}${ROTAS.dePara}/${id}`).pipe(take(1));
  }

  cadastrarDados(dados: any): Observable<AppResponsePadraoModel> {
    return this.http.post<any>(`${this.url}${ROTAS.dePara}`, dados).pipe(take(1));
  }

  atualizarDados(id: number, dados: any): Observable<AppResponsePadraoModel> {
    return this.http.put<any>(`${this.url}${ROTAS.dePara}/${id}`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${this.url}${ROTAS.dePara}/${id}`).pipe(take(1));
  }

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.dePara}/${ROTAS.selecao}`).pipe(take(1));
  }

  recuperarHistoricoAlteracao(queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<HistoricoAlteracaoModel>> {
    return this.http.get<PaginaResultadoPiweb<HistoricoAlteracaoModel>>(`${ this.url }/de-para/historico-revisoes`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }

}
