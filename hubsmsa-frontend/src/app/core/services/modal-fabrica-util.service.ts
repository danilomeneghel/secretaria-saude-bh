import { Injectable } from '@angular/core';

import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { MtNgxModalConfirm } from '@shared/modules/modules/mt-ngx-modal/models/mt-ngx-modal-confirm.model';
import { MtNgxModalService } from '@shared/modules/modules/mt-ngx-modal/services/mt-ngx-modal.service';
import { ModalConfirmComponent } from '../../shared/shared-modal/modal/modal-confirm/modal-confirm.component';

@Injectable()
export class FabricaModalService {
  constructor(public mtNgxModalService: MtNgxModalService) {
  }

  modalConfirmarExcluirMestreDetalhe(nomeFuncionalidade: string) {
    const question = MensagemEnum.CONFIRMAR_EXCLUSAO_MESTRE_DETALHE.split('{VALUE}').join(nomeFuncionalidade);
    return this.mtNgxModalService.openDialogConfirm({
      template: ModalConfirmComponent,
      question,
      title: 'Exclusão de registro',
      confirmText: 'confirmar',
      botaoStyle: 'btn btn-danger',
      modalShowMsg: true
    });
  }

  modalConfirmarVoltar(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.abrirModalConfirm('Voltar à tela anterior', texto || MensagemEnum.CONFIRMAR_VOLTAR);
  }

  modalConfirmarInativacao(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.abrirModalConfirm('Inativação de registro', texto || MensagemEnum.CONFIRMAR_INATIVACAO);
  }

  modalConfirmarAlterar(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.abrirModalConfirm('Confirmação de Alteração de Registro', texto || MensagemEnum.CONFIRMAR_ALTERACAO_REGISTRO);
  }

  modalConfirmarExcluir(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.abrirModalConfirm('Confirmação de Exclusão de Registro', texto || MensagemEnum.CONFIRMAR_EXCLUSAO_REGISTRO);
  }

  modalCancelarFormulario(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.abrirModalConfirm('Cancelamento da edição de registro.', texto || MensagemEnum.CONFIRMAR_CANCELAR_FORMULARIO);
  }

  modalConfirmarExcluirRegistro(texto?: MensagemEnum): MtNgxModalConfirm {
    return this.mtNgxModalService.openDialogConfirm({
      template: ModalConfirmComponent,
      question: texto || 'Confirme a exclusão do registro.',
      dangerModal: true,
      title: 'Exclusão de registro',
      confirmText: 'Excluir',
      botaoStyle: 'btn btn-danger',
      cancelText: 'Cancelar',
      modalShowMsg: true
    });
  }

  modalInfo(titulo: string, mensagem: string) {
    const msg = mensagem ? mensagem : 'Não se aplica';
    return this.abrirModalConfirm(titulo, msg, true);
  }

  private abrirModalConfirm(title: string, question: MensagemEnum | string, onlyConfirm?: boolean) {

    return this.mtNgxModalService.openDialogConfirm({
      title,
      question,
      template: ModalConfirmComponent,
      confirmText: 'Ok',
      onlyConfirm
    });
  }
}
