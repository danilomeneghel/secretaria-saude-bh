import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { LogTiposDeParaService } from '../../services/log-tipos-de-para.service';

@Component({
  selector: 'sbapp-visualizar-log-tipos-de-para',
  templateUrl: './visualizar-log-tipos-de-para.component.html',
  styleUrls: ['./visualizar-log-tipos-de-para.component.css']
})
export class VisualizarLogTiposDeParaComponent implements OnInit, FswVisualizarPadraoModel {

  historico: HistoricoAlteracaoDetalhe;
  id: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    protected logTipoDeParaService: LogTiposDeParaService,
    private router: Router
  ) { }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.logTipoDeParaService.recuperarHistoricoDetalhe(this.id)
      .subscribe((historico: HistoricoAlteracaoDetalhe) => this.historico = historico);
  }

  voltar() {
    const parentRouter:String = this.logTipoDeParaService.moduleParentRouter;
    this.router.navigate(  [parentRouter] );
  }

}
