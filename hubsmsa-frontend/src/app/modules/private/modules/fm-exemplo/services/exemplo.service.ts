import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { take } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from '../../../../../../environments/environment';
import { HttpUtilService } from '@core/services/http-util.service';
import { PesquisarExemploDtoModel } from '../models/dtos/pesquisar-exemplo-dto.model';
import { RecuperarExemploDtoModel } from '../models/dtos/recuperar-exemplo-dto.model';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { EditarExemploDtoModel } from '../models/dtos/editar-exemplo-dto.model';
import { CadastrarExemploDtoModel } from '../models/dtos/cadastrar-exemplo-dto.model';
import { QueryParamsExemploModel } from '../models/query-params-exemplo.model';
import { ParamsConsultaDto } from '@shared/models/interface/dtos/params-consulta-dto';
import { HistoricoAlteracaoModel } from '@shared/models/interface/historico-alteracao.model';
import { Router } from '@angular/router';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';

@Injectable({
  providedIn: 'root'
})
export class ExemploService extends FswHttpService {
  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/exemplo';
  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router
  ) {
    super(router);
  }

  pesquisarDados(queryParamConsulta: QueryParamsExemploModel): Observable<PaginaResultadoPiweb<PesquisarExemploDtoModel>> {
    return this.http.get<PaginaResultadoPiweb<PesquisarExemploDtoModel>>(`${ this.url }/exemplo`, {
      params: this.httpUtil.httpParamsByObjeto(queryParamConsulta)
    }).pipe(take(1));
  }

  cadastrarDados(dados: CadastrarExemploDtoModel): Observable<AppResponsePadraoModel> {
    return this.http.post<AppResponsePadraoModel>(`${ this.url }/exemplo`, dados).pipe(take(1));
  }

  recuperarDados(id: number): Observable<RecuperarExemploDtoModel> {
    return this.http.get<RecuperarExemploDtoModel>(`${ this.url }/exemplo/${ id }`).pipe(take(1));
  }

  atualizarDados(id: number, dados: EditarExemploDtoModel): Observable<AppResponsePadraoModel> {
    return this.http.put<AppResponsePadraoModel>(`${ this.url }/exemplo/${ id }`, dados).pipe(take(1));
  }

  removerDados(id: number): Observable<AppResponsePadraoModel> {
    return this.http.delete<AppResponsePadraoModel>(`${ this.url }/exemplo/${ id }`).pipe(take(1));
  }

  recuperarHistoricoAlteracao(id: number, queryParams: ParamsConsultaDto): Observable<PaginaResultadoPiweb<HistoricoAlteracaoModel>> {
    return this.http.get<PaginaResultadoPiweb<HistoricoAlteracaoModel>>(`${ this.url }/exemplo/historico-revisoes/${ id }`, {
      params: this.httpUtil.httpParamsByObjeto(queryParams)
    });
  }
}
