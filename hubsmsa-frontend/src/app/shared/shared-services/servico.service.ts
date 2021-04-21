import { SelecaoDTO } from './../shared-models/interface/dtos/selecao-dto';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { FswHttpService } from '@shared/models/class/fsw-crud/fsw-http.service';
import { Servico } from '@shared/models/interface/dtos/servico.model';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';
import { take } from 'rxjs/operators';

const ROTAS = {
  servicos: '/servicos',
  sistema: '/sistema',
  selecao: '/selecao',
}

@Injectable()
export class ServicoService extends FswHttpService {
  readonly url = environment.apiUrl;
  moduleParentRouter = '/admin/servicos';

  tempFiltroPesquisa: FormGroup;

  constructor(
    private http: HttpClient,

    router: Router
  ) {
    super(router);
  }

  consultarServicosDoSistema(idSistema: number): Observable<SelecaoDTO[]> {
    return this.http.get<SelecaoDTO[]>(`${this.url}${ROTAS.servicos}/${idSistema}${ROTAS.selecao}`).pipe(take(1));
  }

  recuperarServicos(): Observable<Servico[]> {
    return this.http.get<Servico[]>(`${this.url}${ROTAS.servicos}`).pipe(take(1));
  }

  recuperarDados(id: number): Observable<Servico> {
    return this.http.get<Servico>(`${this.url}${ROTAS.servicos}/${id}`).pipe(take(1));
  }




}
