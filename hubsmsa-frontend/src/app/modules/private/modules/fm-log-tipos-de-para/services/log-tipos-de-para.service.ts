import { LogTiposDePara } from './../../../../../shared/shared-models/interface/dtos/log-tipos-de-para';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { HttpClient } from '@angular/common/http';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { take } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';
import { QueryParamsLogTiposDePara } from '../models/query-params-log-tipos-de-para.model';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { QueryParamsEmpresaModel } from '../../fm-empresas/models/query-params-empresa-.model';

const ROTAS = {
  tiposDePara: '/tipos-de-para',
  logTiposDePara: '/log-tipos-de-para',
  revisoes: '/historico-revisoes',
  csv: '/csv',
  excel: '/excel',
  log: '/log'
};

@Injectable()
export class LogTiposDeParaService extends FswHttpService {
  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/log-tipo-de-para';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
      super(router);
    }

  pesquisarDados(queryParamConsulta: QueryParamsLogTiposDePara): Observable<PaginaResultadoPiweb<LogTiposDePara>> {
    if(queryParamConsulta.itensPorPagina == 20){
      queryParamConsulta.itensPorPagina = 5;
    }
    const params = this.httpUtil.objToUrl(queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<LogTiposDePara>>(`${ this.url }${ROTAS.tiposDePara}${ROTAS.logTiposDePara}${params}`).pipe(take(1));
  }

  recuperarHistoricoDetalhe(id: number): Observable<HistoricoAlteracaoDetalhe> {
    return this.http.get<HistoricoAlteracaoDetalhe>(`${ this.url }${ROTAS.tiposDePara}${ROTAS.revisoes}/${id}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.tiposDePara}${ROTAS.csv}${ROTAS.log}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      console.log(res);

      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    });
  }

  gerarExcel(_queryParamConsulta?: QueryParamsEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.tiposDePara}${ROTAS.excel}${ROTAS.log}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      console.log(res);

      this.httpUtil.baixarArquivo(res, ArquivoEnum.EXCEL);
    }, error => {
      console.log(error);
    });
  }
}
