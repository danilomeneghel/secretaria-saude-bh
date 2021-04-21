import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { HistoricoAlteracaoDetalhe } from '@shared/models/interface/dtos/historico-alteracao-detalhe';

@Component({
  selector: 'sbapp-visualizar-log-empresas',
  templateUrl: './visualizar-log-empresas.component.html',
  styleUrls: ['./visualizar-log-empresas.component.css']
})
export class VisualizarLogEmpresasComponent implements OnInit, FswVisualizarPadraoModel {
  historico: HistoricoAlteracaoDetalhe;
  id: number;

  constructor(
    private activatedRoute: ActivatedRoute,
    protected empresaService: EmpresaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.empresaService.recuperarHistoricoDetalhe(this.id)
      .subscribe((historico: HistoricoAlteracaoDetalhe) => this.historico = historico);
  }


  voltar() {
    const parentRouter:String = this.empresaService.moduleLogParentRouter;
    this.router.navigate(  [parentRouter] );  }
}
