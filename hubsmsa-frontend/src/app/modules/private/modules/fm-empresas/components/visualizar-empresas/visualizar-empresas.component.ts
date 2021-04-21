import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { EmpresaService } from 'app/shared/shared-services/empresa.service';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { statusEnumMensagemVisualizar } from '@shared/models/enum/status.enum';

@Component({
  selector: 'sbapp-visualizar-empresas',
  templateUrl: './visualizar-empresas.component.html',
  styleUrls: ['./visualizar-empresas.component.css']
})
export class VisualizarEmpresasComponent implements OnInit, FswVisualizarPadraoModel {
  empresa: Empresa;
  id: number;
  statusEnum = statusEnumMensagemVisualizar;

  constructor(
    private activatedRoute: ActivatedRoute,
    private empresaService: EmpresaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.empresaService.recuperarDados(this.id)
      .subscribe((empresa: Empresa) => this.empresa = empresa);
  }


  voltar() {
    const parentRouter:String = this.empresaService.moduleLogParentRouter;
    this.router.navigate(  [parentRouter] );  }
}
