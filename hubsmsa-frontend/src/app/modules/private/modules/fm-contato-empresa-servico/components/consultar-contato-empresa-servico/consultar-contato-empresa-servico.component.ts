import { ServicoService } from '../../../../../../shared/shared-services/servico.service';
import { SistemaService } from 'app/shared/shared-services/sistema.service';
import { FormBuilder } from '@angular/forms';
import { ContatoEmpresaService } from '../../../fm-contato-empresas/services/contato-empresa.service';
import { EmpresaService } from '../../../../../../shared/shared-services/empresa.service';
import { Component, OnInit } from '@angular/core';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { Empresa } from '@shared/models/interface/dtos/empresa.model';
import { SelecaoDTO } from '@shared/models/interface/dtos/selecao-dto';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { Observable } from 'rxjs';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { map } from 'rxjs/operators';
import { Sistema } from '@shared/models/interface/dtos/sistema.model';
import { ContatoEmpresaServicoService } from '../../services/contato-empresa-servico.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';
import { ContatoEmpresaServicoDTO } from '../../models/dto/contato-empresa-servico-dto.model';

const CAMPOS = {
  idEmpresa: 'idEmpresa',
  idContato: 'idContato',
  idSistema: 'idSistema',
  idServico: 'idServico',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}

@Component({
  selector: 'sbapp-consultar-contato-empresa-servico',
  templateUrl: './consultar-contato-empresa-servico.component.html',
  styleUrls: ['./consultar-contato-empresa-servico.component.css']
})
export class ConsultarContatoEmpresaServicoComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<ContatoEmpresaServicoDTO>;
  readonly itensPorPaginaPadrao: number = 20;
  readonly paginaPadrao: number = 1;
  listaEmpresasObs: Observable<Empresa[]>;
  listaContatoEmpresaObs: Observable<SelecaoDTO[]>;
  listaSistemaObs: Observable<Sistema[]>;
  listaServicoObs: Observable<SelecaoDTO[]>;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };

  constructor(
    // private contatoServicoService: ContatoServicoService,
    private empresaService: EmpresaService,
    private contatoEmpresaService: ContatoEmpresaService,
    private contatoEmpresaServicoService: ContatoEmpresaServicoService,
    private sistemaService: SistemaService,
    private servicoService: ServicoService,
    private formBuilder: FormBuilder,
    private fabricaModalService: FabricaModalService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService,
  ) {
    super(validacaoService, contatoEmpresaServicoService, ngcAlert);
  }

  ngOnInit() {
    this.criarFormulario();
    this.ouvirCampoEmpresa();
    this.ouvirCampoSistema();
    this.recuperarSistemas()
    this.recuperarEmpresas();
  }


  ouvirCampoEmpresa() {
    this.formPesquisa.get(CAMPOS.idEmpresa).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampo(valor, CAMPOS.idContato);
        this.recuperarContatosEmpresa(valor);
      }
    );
  }

  ouvirCampoSistema() {
    this.formPesquisa.get(CAMPOS.idSistema).valueChanges.subscribe(
      (valor: number) => {
        if (typeof (valor) != 'number') {
          valor = null
        }
        this.habilitarCampo(valor, CAMPOS.idServico);
        this.recuperarServicos(valor);
      }
    );
  }

  btnPesquisar(pagina?: PiwebPaginaEvt): void {
    this.ngcAlert.clearAll();
    this.pesquisarDados(pagina);
  }

  excluirContatoEmpresaServico(idContatoEmpresaServico: number) {
    this.fabricaModalService.modalConfirmarExcluirRegistro().onActionEvt.subscribe((confirm) => {
      if (confirm) {
        this.contatoEmpresaServicoService.removerDados(idContatoEmpresaServico).subscribe(() => { this.pesquisarDados()});
      }
    });
  }

  recuperarEmpresas() {
    this.listaEmpresasObs = this.empresaService.pesquisarDadosDroplist(null).pipe(
      map((listaEmpresas) => {
        return listaEmpresas.itens;
      }
      ));
  }

  recuperarServicos(idSistema: number) {
    if (idSistema){
      this.ngcAlert.clearAll();
      this.listaServicoObs = this.servicoService.consultarServicosDoSistema(idSistema);
    }
  }

  habilitarCampo(valor: number, nomeCampo: string) {
    if (valor) {
      this.formPesquisa.get(nomeCampo).enable();
    } else {
      this.formPesquisa.get(nomeCampo).disable();
    }
  }

  recuperarContatosEmpresa(idEmpresa: number) {
    if (idEmpresa){
      this.ngcAlert.clearAll();
      this.listaContatoEmpresaObs = this.contatoEmpresaService.consultarContatosDaEmpresa(idEmpresa);
    }

  }

  recuperarSistemas() {
      this.listaSistemaObs = this.sistemaService.recuperarSistemas();

  }

  criarFormulario(): void {
    this.formPesquisa = this.formBuilder.group({
      [CAMPOS.idEmpresa]: [],
      [CAMPOS.idContato]: [{ value: null, disabled: true }],
      [CAMPOS.idSistema]: [],
      [CAMPOS.idServico]: [{ value: null, disabled: true }],

      [CAMPOS.ordem]: [],
      [CAMPOS.pagina]: [],
      [CAMPOS.coluna]: [],
      [CAMPOS.itensPorPagina]: []
    });
  }

}
