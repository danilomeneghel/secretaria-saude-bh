import { Component, OnInit } from '@angular/core';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { statusEnumMensagemVisualizar } from '@shared/models/enum/status.enum';
import { ActivatedRoute, Router } from '@angular/router';
import { SistemaService } from 'app/shared/shared-services/sistema.service';

@Component({
  selector: 'sbapp-visualizar-sistemas',
  templateUrl: './visualizar-sistemas.component.html',
  styleUrls: ['./visualizar-sistemas.component.css']
})

export class VisualizarSistemasComponent implements OnInit, FswVisualizarPadraoModel {
  sistema: Sistema;
  id: number;
  statusEnum = statusEnumMensagemVisualizar;

  constructor(
    private activatedRoute: ActivatedRoute,
    private SistemasService: SistemaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.SistemasService.recuperarDados(this.id)
      .subscribe((sistemas: Sistema) => this.sistema = sistemas);
  }

  voltar() {
    const parentRouter:String = this.SistemasService.moduleParentRouter;
    this.router.navigate(  [parentRouter] );  
  }
}
