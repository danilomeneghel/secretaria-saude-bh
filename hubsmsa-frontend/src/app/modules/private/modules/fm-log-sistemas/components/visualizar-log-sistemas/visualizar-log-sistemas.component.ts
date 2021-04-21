import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';

@Component({
  selector: 'sbapp-visualizar-log-sistemas',
  templateUrl: './visualizar-log-sistemas.component.html',
  styleUrls: ['./visualizar-log-sistemas.component.css']
})

export class VisualizarLogSistemasComponent implements OnInit, FswVisualizarPadraoModel {
  historico: HistoricoAlteracaoDetalhe;
  id: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    protected sistemaService: SistemaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.sistemaService.recuperarHistoricoDetalhe(this.id)
      .subscribe((historico: HistoricoAlteracaoDetalhe) => this.historico = historico);
  }

  voltar() {
    const parentRouter:String = this.sistemaService.moduleLogParentRouter;
    this.router.navigate(  [parentRouter] );  
  }
}
