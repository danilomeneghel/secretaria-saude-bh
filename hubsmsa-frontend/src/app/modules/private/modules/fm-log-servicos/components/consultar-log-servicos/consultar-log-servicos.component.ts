import { statusExecucaoEnumMensagem } from './../../../../../../shared/shared-models/enum/status-excecucao.enum';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputListaCheckboxModel } from '@shared/components/components/input-lista-checkbox/model/input-lista-checkbox.model';
import { FswPesquisarComponentModel } from '@shared/models/class/fsw-crud/fsw-pesquisar-component.model';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';
import { FswPesquisarModel } from '@shared/models/interface/fsw-crud/fsw-pesquisar.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { PaginaResultadoPiweb, PiwebPaginaEvt } from '@shared/modules/modules/piweb-tabela';
import { ValidacaoFormularioService } from '@shared/modules/modules/validacao-formulario/services/validacao-formulario.service';
import { Observable } from 'rxjs';
import { LogServicosService } from '../../services/log-servicos.service';
import { Servico } from '@shared/models/interface/dtos/servico.model';
import { ServicoService } from 'app/shared/shared-services/servico.service';
import { LogServico } from '@shared/models/interface/dtos/log-servico.model';
import { dataSourceCheckboxStatusExecucao } from '@shared/models/constants';

const CAMPOS = {
  idServico: 'idServico',
  dataInicial: 'dataInicial',
  dataFinal: 'dataFinal',
  status: 'status',

  ordem: 'ordem',
  pagina: 'pagina',
  coluna: 'coluna',
  itensPorPagina: 'itensPorPagina',
}
@Component({
  selector: 'sbapp-consultar-log-servicos',
  templateUrl: './consultar-log-servicos.component.html',
  styleUrls: ['./consultar-log-servicos.component.css']
})
export class ConsultarLogServicosComponent extends FswPesquisarComponentModel implements OnInit, FswPesquisarModel {

  formPesquisa: FormGroup;
  opcoesCheckboxStatus: InputListaCheckboxModel[];
  listaServicosObs: Observable<Servico[]>;
  permissaoEnum = PermissaoUsuarioEnum;
  resultadoPaginaAtual: PaginaResultadoPiweb<LogServico>;
  readonly itensPorPaginaPadrao: number = 5;
  readonly paginaPadrao: number = 1;
  statusEnum = statusExecucaoEnumMensagem;
  paginaAtualCfg: PiwebPaginaEvt = { pagina: this.paginaPadrao, itensPorPagina: this.itensPorPaginaPadrao };
  id: number;

  constructor(
    private formBuilder: FormBuilder,
    protected logServicosService: LogServicosService,
    private servicoService: ServicoService,
    protected validacaoService: ValidacaoFormularioService,
    protected ngcAlert: NgcAlertService) {
      super(validacaoService, logServicosService, ngcAlert);
    }

    ngOnInit() {
      this.criarFormulario();
      this.preencherServicos();
    }

    criarFormulario() {
      this.formPesquisa = this.formBuilder.group({
        [CAMPOS.dataInicial]: [],
        [CAMPOS.dataFinal]: [],
        [CAMPOS.idServico]: [],
        [CAMPOS.status]: [],

        [CAMPOS.ordem]: [],
        [CAMPOS.pagina]: [],
        [CAMPOS.coluna]: [],
        [CAMPOS.itensPorPagina]: []
      });
      this.opcoesCheckboxStatus = dataSourceCheckboxStatusExecucao;

    }

    preencherServicos() {
      this.listaServicosObs = this.servicoService.recuperarServicos();
    }

    btnPesquisar(_pagina?: PiwebPaginaEvt): void {
      this.ngcAlert.clearAll()

      this.pesquisarDados(_pagina);
    }

}
