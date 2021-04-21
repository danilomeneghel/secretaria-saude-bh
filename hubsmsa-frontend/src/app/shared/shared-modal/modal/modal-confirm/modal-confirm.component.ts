import { Component, OnInit } from '@angular/core';
import { MtNgxModalConfirm } from '@shared/modules/modules/mt-ngx-modal/models/mt-ngx-modal-confirm.model';
import { MtNgxModalService } from '@shared/modules/modules/mt-ngx-modal/services/mt-ngx-modal.service';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'mt-ngx-modal-confirm',
  templateUrl: './modal-confirm.component.html',
  styleUrls: ['./modal-confirm.component.css']
})
export class ModalConfirmComponent implements OnInit {
  modalRef: MtNgxModalConfirm;

  constructor(
    private nguModalService: MtNgxModalService
  ) {
  }

  ngOnInit() {
    this.modalRef = this.nguModalService.getLatestModalOpened<MtNgxModalConfirm>();
  }

  confirm(valor) {
    if (!valor) {
      this.close();
    } else if ((this.modalRef.mtNgxModalConfig.closeOnAnyBtnClick && valor)) {
      this.modalRef.onActionEvt.next(valor);
      this.close();
    }
  }

  close() {
    this.nguModalService.closeModal(this.modalRef.mtNgxModalConfig.id);
    setTimeout(() => {
      this.modalRef.onActionEvt.complete();
    }, 1000);
  }

  modalBodyQuestion() {
    if (this.modalRef && this.modalRef.mtNgxModalConfig) {
      return this.modalRef.mtNgxModalConfig.question || 'Por favor, adicione uma pergunta ("question").';
    }

  }

}
