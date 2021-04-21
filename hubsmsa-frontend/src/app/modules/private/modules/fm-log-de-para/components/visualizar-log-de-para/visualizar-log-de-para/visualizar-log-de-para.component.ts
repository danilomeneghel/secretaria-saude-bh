import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { LogDeParaService } from '../../../services/log-de-para.service';

@Component({
  selector: 'sbapp-visualizar-log-de-para',
  templateUrl: './visualizar-log-de-para.component.html',
  styleUrls: ['./visualizar-log-de-para.component.css']
})
export class VisualizarLogDeParaComponent implements OnInit, FswVisualizarPadraoModel {

  historico: HistoricoAlteracaoDetalhe;
  id: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    protected logDeParaService: LogDeParaService,
    private router: Router
  ) { }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.logDeParaService.recuperarHistoricoDetalhe(this.id)
      .subscribe((historico: HistoricoAlteracaoDetalhe) => this.historico = historico);
  }

  voltar() {
    const parentRouter:String = this.logDeParaService.moduleLogParentRouter;
    this.router.navigate(  [parentRouter] );
  }

}
