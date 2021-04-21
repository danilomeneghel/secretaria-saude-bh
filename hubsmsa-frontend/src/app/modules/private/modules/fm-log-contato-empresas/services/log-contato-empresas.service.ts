import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { QueryParamsContatoEmpresaModel } from '../../fm-contato-empresas/models/query-params-contato-empresa.model';
import { LogContatoEmpresa } from '@shared/models/interface/dtos/log-contato-empresa.model';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';

const ROTAS = {
  empresas: '/empresa',
  selecao: 'selecao',
  sistemas: 'sistemas',
  contatoEmpresas: '/contato-empresas',
  revisoes: '/historico-revisoes',
  csv: '/csv',
  excel: '/excel',
  log: '/log'
};

@Injectable({
  providedIn: 'root'
})
export class LogContatoEmpresasService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/log-empresa';
  moduleLogParentRouter = '/admin/log-contato-empresas';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
      super(router);
  }

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.empresas}/${ROTAS.selecao}`).pipe(take(1));
  }

  recuperarDados(id: number): Observable<LogContatoEmpresa> {
    return this.http.get<LogContatoEmpresa>(`${this.url}${ROTAS.empresas}/${id}`).pipe(take(1));
  }

  recuperarHistoricoAlteracao(queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<HistoricoAlteracaoModel>> {
    return this.http.get<PaginaResultadoPiweb<HistoricoAlteracaoModel>>(`${ this.url }${ROTAS.contatoEmpresas}${ROTAS.revisoes}`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }

  recuperarHistoricoDetalhe(id: number): Observable<HistoricoAlteracaoDetalhe> {
    return this.http.get<HistoricoAlteracaoDetalhe>(`${ this.url }${ROTAS.contatoEmpresas}${ROTAS.revisoes}/${id}`).pipe(take(1));
  }

  gerarCsv(_queryParamConsulta?: QueryParamsContatoEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.contatoEmpresas}${ROTAS.csv}${ROTAS.log}`, {
      params: this.httpUtil.httpParamsByObjeto(_queryParamConsulta),
      observe: 'response',
      responseType: 'blob' as 'json'
    }).subscribe(res => {
      this.httpUtil.baixarArquivo(res, ArquivoEnum.CSV);
    }, error => {
      console.log(error);
    });
  }

  gerarExcel(_queryParamConsulta?: QueryParamsContatoEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.contatoEmpresas}${ROTAS.excel}${ROTAS.log}`, {
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
