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
import { QueryParamsEmpresaModel } from '../../modules/private/modules/fm-empresas/models/query-params-empresa-.model';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';

const ROTAS = {
  empresas: '/empresa',
  selecao: 'selecao',
  sistemas: 'sistemas',
  csv: '/csv',
  excel: '/excel',
  dePara: '/de-para',
  droplist: '/droplist',
  revisoes: '/historico-revisoes'

}

@Injectable()
export class EmpresaService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/empresas';
  moduleLogParentRouter = '/admin/log-empresas';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,

    router: Router
  ) {
    super(router);
  }

  pesquisarDados(_queryParamConsulta?: QueryParamsEmpresaModel): Observable<PaginaResultadoPiweb<Empresa>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<Empresa>>(`${this.url}${ROTAS.empresas}${params}`).pipe(take(1));
  }

  pesquisarDadosDroplist(_queryParamConsulta?: QueryParamsEmpresaModel): Observable<PaginaResultadoPiweb<Empresa>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);
    return this.http.get<PaginaResultadoPiweb<Empresa>>(`${this.url}${ROTAS.empresas}${ROTAS.droplist}${params}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsEmpresaModel, addRota: string = '') {
    this.http.get(`${this.url}${ROTAS.empresas}${ROTAS.csv}${addRota}`, {
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

  gerarExcel(_queryParamConsulta?: QueryParamsEmpresaModel, addRota: string = '') {
    this.http.get(`${this.url}${ROTAS.empresas}${ROTAS.excel}${addRota}`, {
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
  recuperarDados(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.url}${ROTAS.empresas}/${id}`).pipe(take(1));
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

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.empresas}/${ROTAS.selecao}`).pipe(take(1));
  }

  consultarSistemasDaEmpresa(idEmpresa: number): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.empresas}/${idEmpresa}/${ROTAS.sistemas}`).pipe(take(1));
  }

  recuperarHistoricoAlteracao(queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<HistoricoAlteracaoModel>> {
    return this.http.get<PaginaResultadoPiweb<HistoricoAlteracaoModel>>(`${ this.url }${ROTAS.empresas}${ROTAS.revisoes}`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }

  recuperarHistoricoDetalhe(id: number): Observable<HistoricoAlteracaoDetalhe> {
    return this.http.get<HistoricoAlteracaoDetalhe>(`${ this.url }${ROTAS.empresas}${ROTAS.revisoes}/${id}`).pipe(take(1));
  }

}
