import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContatoEmpresaService } from '../../services/contato-empresa.service';
import { ContatoEmpresa } from '../../models/contato-empresa.model';
import { FswVisualizarPadraoModel } from '@shared/models/interface/fsw-crud/fsw-visualizar-padrao.model';
import { statusEnumMensagemVisualizar } from '@shared/models/enum/status.enum';

@Component({
  selector: 'sbapp-visualizar-contato-empresas',
  templateUrl: './visualizar-contato-empresas.component.html',
  styleUrls: ['./visualizar-contato-empresas.component.css']
})
export class VisualizarContatoEmpresasComponent implements OnInit, FswVisualizarPadraoModel {
  contatoEmpresa: ContatoEmpresa;
  id: number;
  statusEnum = statusEnumMensagemVisualizar;

  constructor(
    private activatedRoute: ActivatedRoute,
    private ContatoEmpresaService: ContatoEmpresaService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.recuperarDados();
  }

  recuperarDados() {
    this.id = this.activatedRoute.snapshot.params.id;
    this.ContatoEmpresaService.recuperarDados(this.id)
      .subscribe((contatoEmpresa: ContatoEmpresa) => this.contatoEmpresa = contatoEmpresa);
  }


  voltar() {
    const parentRouter:String = this.ContatoEmpresaService.moduleParentRouter;
    this.router.navigate(  [parentRouter] );

  }
}
