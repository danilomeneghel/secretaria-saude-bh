import { Component, OnInit } from '@angular/core';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { statusEnumMensagemVisualizar } from '@shared/models/enum/status.enum';
import { ActivatedRoute, Router } from '@angular/router';
import { DeParaService } from '../../services/de-para.service';
import { VisualizarDeParaDTO } from '../../models/dtos/visualizar/visualizar-depara-dto.model';

@Component({
  selector: 'sbapp-visualizar-de-para',
  templateUrl: './visualizar-de-para.component.html',
  styleUrls: ['./visualizar-de-para.component.css']
})
export class VisualizarDeParaComponent implements OnInit, FswVisualizarPadraoModel {
  dePara: VisualizarDeParaDTO;
  id: number;
  statusEnum = statusEnumMensagemVisualizar;

  constructor(
    private activatedRoute: ActivatedRoute,
    private deParaService: DeParaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.deParaService.visualizarDados(this.id).subscribe((dePara) => this.dePara = dePara);
  }

  voltar() {
    const parentRouter:String = this.deParaService.moduleParentRouter;
    this.router.navigate(  [parentRouter] );  }
}
