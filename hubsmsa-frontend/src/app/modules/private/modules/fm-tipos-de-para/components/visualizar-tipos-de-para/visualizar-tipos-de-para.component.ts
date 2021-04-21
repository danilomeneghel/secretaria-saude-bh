import { Component, OnInit } from '@angular/core';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { TiposDePara } from '@shared/models/interface/dtos/tipos-de-para';
import { statusEnumMensagemVisualizar } from '@shared/models/enum/status.enum';
import { ActivatedRoute, Router } from '@angular/router';
import { TiposDeParaService } from 'app/shared/shared-services/tipos-de-para.service';

@Component({
  selector: 'sbapp-visualizar-tipos-de-para',
  templateUrl: './visualizar-tipos-de-para.component.html',
  styleUrls: ['./visualizar-tipos-de-para.component.css']
})
export class VisualizarTiposDeParaComponent implements OnInit, FswVisualizarPadraoModel {
  tipoDePara: TiposDePara;
  id: number;
  statusEnum = statusEnumMensagemVisualizar;

  constructor(
    private activatedRoute: ActivatedRoute,
    private TipoDeParaService: TiposDeParaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.TipoDeParaService.recuperarDados(this.id)
      .subscribe((tipoDePara: TiposDePara) => this.tipoDePara = tipoDePara);
  }


  voltar() {
    const parentRouter:String = this.TipoDeParaService.moduleParentRouter;
    this.router.navigate(  [parentRouter] );  }
}
