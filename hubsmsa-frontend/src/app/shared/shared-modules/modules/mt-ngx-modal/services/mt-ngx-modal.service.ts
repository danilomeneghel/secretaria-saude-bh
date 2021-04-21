import { Injectable } from '@angular/core';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';
import { Subject } from 'rxjs';
import { MtNgxModalConfig } from '../models/mt-ngx-modal-config.model';
import { MtNgxModalConfirmConfig } from '../models/mt-ngx-modal-confirm-config.model';
import { MtNgxModalConfirm } from '../models/mt-ngx-modal-confirm.model';
import { MtNgxModal } from '../models/mt-ngx-modal.model';

@Injectable({
  providedIn: 'root'
})
export class MtNgxModalService {
  private modals: MtNgxModal[] = [];
  private bsModalRefs: Map<string, BsModalRef> = new Map();

  private latestModal: any;

  constructor(
    public bsModalService: BsModalService
  ) {
    this.bsModalService.config.ignoreBackdropClick = true;
  }

  openDialog(config: MtNgxModalConfig) {
    this.definirCfgPadrao(config);

    const mtNgxNewModal: MtNgxModal = {
      mtNgxModalConfig: config,
    };

    this.addModalToList(config, mtNgxNewModal);

    const bsModalRef = this.bsModalService.show(config.template, config.ngxModalOptions);
    this.bsModalRefs.set(config.id, bsModalRef);

    return mtNgxNewModal;
  }

  openDialogConfirm(config: MtNgxModalConfirmConfig): MtNgxModalConfirm {
    if (!config.botaoStyle) {
      config.botaoStyle = 'btn-success';
    }
    this.definirCfgPadrao(config);

    const mtNgxNewModal: MtNgxModalConfirm = {
      mtNgxModalConfig: config,
      onActionEvt: new Subject<boolean>()
    };

    mtNgxNewModal.onAction = mtNgxNewModal.onActionEvt.asObservable();
    this.addModalToList(config, mtNgxNewModal);

    const bsModalRef = this.bsModalService.show(config.template, config.ngxModalOptions);
    this.bsModalRefs.set(config.id, bsModalRef);

    return mtNgxNewModal;
  }

  private definirCfgPadrao(config: MtNgxModalConfig) {
    if (config) {
      if (config.id == null) {
        config.id = this.generateHashId();
      }
      if (config.closeOnAnyBtnClick == null) {
        config.closeOnAnyBtnClick = true;
      }
      if (config.ngxModalOptions == null) {
        config.ngxModalOptions = {
          class: 'modal-dialog-centered'
        };
      }
    }
  }

  closeModal(id: string) {
    try {
      this.modals = this.modals.filter(x => x.mtNgxModalConfig.id !== id);
      this.bsModalRefs.get(id).hide();
      this.bsModalRefs.delete(id);
    } catch (error) {
      console.error(error);
    }
  }

  closeAll() {
    if (this.modals) {
      const ids = this.modals.map(modal => modal.mtNgxModalConfig.id);
      ids.forEach(id => this.closeModal(id));
    }
  }

  getLatestModalOpened<T>(): T {
    return this.latestModal;
  }

  private addModalToList(mtNgxModalConfig: MtNgxModalConfig, mtNgxNewModal: any) {
    if (
      !this.modals.find(x => x.mtNgxModalConfig.id === mtNgxModalConfig.id)
    ) {
      this.modals.push(mtNgxNewModal);
      this.latestModal = mtNgxNewModal;
    }
  }

  private generateHashId(): string {
    let text = '';
    const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    for (let i = 0; i < 5; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
  }
}





