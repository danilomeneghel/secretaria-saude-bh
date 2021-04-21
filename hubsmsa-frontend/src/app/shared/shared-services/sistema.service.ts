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
import { Sistema } from '../shared-models/interface/dtos/sistema.model';
import { LogSistema } from '@shared/models/interface/dtos/log-sistema.model';
import { QueryParamsSistemaModel } from '../../modules/private/modules/fm-sistemas/models/query-params-sistema.model';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';

const ROTAS = {
  sistemas: '/sistemas',
  empresa:'/empresa',
  all: '/all',
  csv: '/csv',
  excel: '/excel',
  revisoes: '/historico-revisoes'
}

@Injectable()
export class SistemaService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/sistemas';
  moduleLogParentRouter = '/admin/log-sistemas';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,

    router: Router
  ) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta: Partial<QueryParamsSistemaModel>): Observable<PaginaResultadoPiweb<Sistema>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<Sistema>>(`${this.url}${ROTAS.sistemas}${params}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsSistemaModel, addRota : string = '') {
    this.http.get(`${this.url}${ROTAS.sistemas}${ROTAS.csv}${addRota}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    })
  }

  gerarExcel(_queryParamConsulta?: QueryParamsSistemaModel, addRota : string = '') {
    this.http.get(`${this.url}${ROTAS.sistemas}${ROTAS.excel}${addRota}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.EXCEL);
    }, error => {
      console.log(error);
    })
  }

  recuperarDados(id: number): Observable<Sistema> {
    return this.http.get<Sistema>(`${this.url}${ROTAS.sistemas}/${id}`).pipe(take(1));
  }

  recuperarSistemas(): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${this.url}${ROTAS.sistemas}${ROTAS.all}`).pipe(take(1));
  }

  recuperarSistemasPorEmpresa(idEmpresa: number): Observable<Sistema[]> {
    return this.http.get<Sistema[]>(`${this.url}${ROTAS.sistemas}/${idEmpresa}${ROTAS.empresa}`).pipe(take(1));
  }

  recuperarDadosLog(id: number): Observable<LogSistema> {
    return this.http.get<LogSistema>(`${this.url}${ROTAS.sistemas}/${id}`).pipe(take(1));
  }

  cadastrarDados(dados: any): Observable<AppResponsePadraoModel> {
    return this.http.post<any>(`${this.url}${ROTAS.sistemas}`, dados).pipe(take(1));
  }

  atualizarDados(id: number, dados: any): Observable<AppResponsePadraoModel> {
    return this.http.put<any>(`${this.url}${ROTAS.sistemas}/${id}`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${this.url}${ROTAS.sistemas}/${id}`).pipe(take(1));
  }

  recuperarHistoricoAlteracao(queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<HistoricoAlteracaoModel>> {
    return this.http.get<PaginaResultadoPiweb<HistoricoAlteracaoModel>>(`${ this.url }/sistemas/historico-revisoes`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }

  recuperarHistoricoDetalhe(id: number): Observable<HistoricoAlteracaoDetalhe> {
    return this.http.get<HistoricoAlteracaoDetalhe>(`${ this.url }${ROTAS.sistemas}${ROTAS.revisoes}/${id}`).pipe(take(1));
  }

}
