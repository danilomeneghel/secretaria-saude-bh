import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpUtilService } from '@core/services/http-util.service';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { LogServico } from '@shared/models/interface/dtos/log-servico.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { PaginaResultadoPiweb } from '@shared/modules/modules/piweb-tabela';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { QueryParamsContatoServico } from '../../fm-contato-empresa-servico/models/query-params.model';


const ROTAS = {
  logServicos: '/log-servicos',
  selecao: 'selecao',
  csv: '/csv',
  excel: '/excel',

};

@Injectable({
  providedIn: 'root'
})
export class LogServicosService extends FswHttpService {

  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin';
  tempFiltroPesquisa: FormGroup;

  constructor(private http: HttpClient,
    private httpUtil: HttpUtilService,
    router: Router) {
      super(router);
  }

  recuperarSelecao(): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.logServicos}/${ROTAS.selecao}`).pipe(take(1));
  }

  pesquisarDados(_queryParamConsulta: QueryParamsContatoServico): Observable<PaginaResultadoPiweb<LogServico>> {
    const params = this.httpUtil.objToUrl(_queryParamConsulta);

   return this.http.get<PaginaResultadoPiweb<LogServico>>(`${this.url}${ROTAS.logServicos}${params}`).pipe(take(1));
 }
}
