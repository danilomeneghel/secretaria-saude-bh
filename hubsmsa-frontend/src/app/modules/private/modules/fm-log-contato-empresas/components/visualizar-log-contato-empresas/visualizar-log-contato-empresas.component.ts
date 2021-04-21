import { LogContatoEmpresasService } from './../../services/log-contato-empresas.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';

@Component({
  selector: 'sbapp-visualizar-log-contato-empresas',
  templateUrl: './visualizar-log-contato-empresas.component.html',
  styleUrls: ['./visualizar-log-contato-empresas.component.css']
})
export class VisualizarLogContatoEmpresasComponent implements OnInit, FswVisualizarPadraoModel {

  historico: HistoricoAlteracaoDetalhe;
  id: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    protected logContatoEmpresaService: LogContatoEmpresasService,
    private router: Router
  ) { }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.logContatoEmpresaService.recuperarHistoricoDetalhe(this.id)
      .subscribe((historico: HistoricoAlteracaoDetalhe) => this.historico = historico);
  }

  voltar() {
    const parentRouter:String = this.logContatoEmpresaService.moduleLogParentRouter;
    this.router.navigate(  [parentRouter] );
  }
}
