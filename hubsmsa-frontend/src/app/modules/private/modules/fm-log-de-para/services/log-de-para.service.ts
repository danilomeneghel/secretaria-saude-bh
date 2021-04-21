import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';
import { LogDePara } from '@shared/models/interface/dtos/log-de-para';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { QueryParamsLogDePara } from '../models/query-params-log-de-para.model';


const ROTAS = {
  dePara: '/de-para',
  logDePara: '/log-de-para',
  revisoes: '/historico-revisoes',
  csv: '/csv',
  excel: '/excel',
  log: '/log'
}


@Injectable({
  providedIn: 'root'
})
export class LogDeParaService extends FswHttpService {
  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/log-tipo-de-para';
  moduleLogParentRouter = '/admin/log-de-para';
  tempFiltroPesquisa: FormGroup;


  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
    super(router);
  }

  pesquisarDados(queryParamConsulta: QueryParamsLogDePara): Observable<PaginaResultadoPiweb<LogDePara>> {
    if(queryParamConsulta.itensPorPagina === 20) {
      queryParamConsulta.itensPorPagina = 5;
    }
    const params = this.httpUtil.objToUrl(queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<LogDePara>>(`${ this.url }${ROTAS.dePara}${ROTAS.logDePara}${params}`).pipe(take(1));
  }

  recuperarHistoricoDetalhe(id: number): Observable<HistoricoAlteracaoDetalhe> {
    return this.http.get<HistoricoAlteracaoDetalhe>(`${ this.url }${ROTAS.dePara}${ROTAS.revisoes}/${id}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsLogDePara) {
    this.http.get(`${this.url}${ROTAS.dePara}${ROTAS.csv}${ROTAS.log}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    });
  }

  gerarExcel(_queryParamConsulta?: QueryParamsLogDePara) {
    this.http.get(`${this.url}${ROTAS.dePara}${ROTAS.excel}${ROTAS.log}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.EXCEL);
    }, error => {
      console.log(error);
    });
  }
}
