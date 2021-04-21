import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { HttpClient } from '@angular/common/http';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { QueryParamsTiposDePara } from '../../modules/private/modules/fm-tipos-de-para/models/query-params-tipos-de-para.model';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { TiposDePara } from '../shared-models/interface/dtos/tipos-de-para';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { take } from 'rxjs/operators';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { FormGroup } from '@angular/forms';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';

const ROTAS = {
  tiposDePara: '/tipos-de-para',
  selecao: '/selecao',
  csv: '/csv',
  excel: '/excel'
}

@Injectable()
export class TiposDeParaService extends FswHttpService {
  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/tipos-de-para';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
      super(router);
    }


  pesquisarDados(queryParamConsulta: QueryParamsTiposDePara): Observable<PaginaResultadoPiweb<TiposDePara>> {
    const params = this.httpUtil.objToUrl(queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<TiposDePara>>(`${ this.url }${ROTAS.tiposDePara}${params}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsTiposDePara) {
    this.http.get(`${this.url}${ROTAS.tiposDePara}${ROTAS.csv}`, {
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

  gerarExcel(_queryParamConsulta?: QueryParamsTiposDePara) {
    this.http.get(`${this.url}${ROTAS.tiposDePara}${ROTAS.excel}`, {
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

  recuperarSelecaoTipoDePara(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.tiposDePara}${ROTAS.selecao}`).pipe(take(1));
  }

  cadastrarDados(dados: TiposDePara): Observable<AppResponsePadraoModel> {
    return this.http.post<AppResponsePadraoModel>(`${ this.url }${ROTAS.tiposDePara}`, dados).pipe(take(1));
  }

  recuperarDados(id: number): Observable<TiposDePara> {
    return this.http.get<TiposDePara>(`${ this.url }${ROTAS.tiposDePara}/${ id }`).pipe(take(1));
  }

  atualizarDados(id: number, dados: TiposDePara): Observable<AppResponsePadraoModel> {
    return this.http.put<AppResponsePadraoModel>(`${ this.url }${ROTAS.tiposDePara}/${ id }`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${ this.url }${ROTAS.tiposDePara}/${ id }`).pipe(take(1));
  }
}
