import { Injectable } from '@angular/core';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { environment } from 'environments/environment';
import { FormGroup } from '@angular/forms';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { DePara } from '../models/de-para.model';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { QueryParamsDePara } from '../models/query-params.model';
import { VisualizarDeParaDTO } from '../models/dtos/visualizar/visualizar-depara-dto.model';
import { HttpUtilService } from '@core/services/http-util.service';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { FiltrosDeParaDTO } from '../models/dtos/pesquisa/filtros-depara-dto.model';

const ROTAS = {
  dePara: '/de-para',
  pesquisar: '/pesquisar',
  visualizar: '/visualizar',
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
    router: Router) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta: QueryParamsDePara): Observable<PaginaResultadoPiweb<DePara>> {
    return this.http.post<PaginaResultadoPiweb<DePara>>(`${this.url}${ROTAS.dePara}${ROTAS.pesquisar}`, _queryParamConsulta).pipe(take(1));
  }

  recuperarDados<T>(id: number): Observable<T> {
    return this.http.get<T>(`${this.url}${ROTAS.dePara}/${id}`).pipe(take(1));
  }

  visualizarDados(id: number): Observable<VisualizarDeParaDTO> {
    return this.http.get<VisualizarDeParaDTO>(`${this.url}${ROTAS.dePara}${ROTAS.visualizar}/${id}`).pipe(take(1));
  }

  cadastrarDados(dados: any): Observable<AppResponsePadraoModel> {
    console.log(dados);
    return this.http.post<any>(`${this.url}${ROTAS.dePara}`, dados).pipe(take(1));
  }

  atualizarDados(id: number, dados: any): Observable<AppResponsePadraoModel> {
    return this.http.put<any>(`${this.url}${ROTAS.dePara}/${id}`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${this.url}${ROTAS.dePara}/${id}`).pipe(take(1));
  }

  gerarDeParaCsv(_queryParamConsulta: FiltrosDeParaDTO) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.http.post(`${this.url}${ROTAS.dePara}${ROTAS.csv}`, _queryParamConsulta, {
      headers,
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      console.log(res);
      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    })
  }

  gerarDeParaExcel(_queryParamConsulta?: FiltrosDeParaDTO) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    this.http.post(`${this.url}${ROTAS.dePara}${ROTAS.excel}`, _queryParamConsulta, {
      headers,
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
