import { MtNgxModalConfig } from './mt-ngx-modal-config.model';

export interface MtNgxModalConfirmConfig extends MtNgxModalConfig {
    question?: string;
    title?: string;
    dangerModal?: boolean;
    modalShowMsg?: boolean;
    cancelText?: string;
    confirmText?: string;
    botaoStyle?: string;
    onlyConfirm?: boolean;
}

