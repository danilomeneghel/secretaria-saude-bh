import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { ArquivoEnum } from '@shared/models/enum/arquivos.enum';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { QueryParamsEmpresaModel } from '../../fm-empresas/models/query-params-empresa-.model';
import { AcessoUsuario } from '../models/acesso-usuario.model';

const ROTAS = {
  usuario: '/usuario',
  acessoUsuario: '/acessos-usuario',
  selecao: 'selecao',
  csv: '/csv',
  excel: '/excel',
  log: '/log'

};
@Injectable({
  providedIn: 'root'
})
export class LogAcessoUsuarioService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
      super(router);
  }

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.usuario}/${ROTAS.selecao}`).pipe(take(1));
  }

  recuperarAcessoUsuario(queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<AcessoUsuario>> {
    return this.http.get<PaginaResultadoPiweb<AcessoUsuario>>(`${ this.url }${ROTAS.usuario}${ROTAS.acessoUsuario}`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }

  gerarCsv(_queryParamConsulta?: QueryParamsEmpresaModel) {
    this.http.get(`${this.url}${ROTAS.usuario}${ROTAS.acessoUsuario}${ROTAS.csv}${ROTAS.log}`, {
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
    this.http.get(`${this.url}${ROTAS.usuario}${ROTAS.acessoUsuario}${ROTAS.excel}${ROTAS.log}`, {
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
