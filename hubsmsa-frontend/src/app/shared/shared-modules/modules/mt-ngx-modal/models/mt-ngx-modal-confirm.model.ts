import { Observable, Subject } from 'rxjs';
import { MtNgxModalConfirmConfig } from './mt-ngx-modal-confirm-config.model';
import { MtNgxModal } from './mt-ngx-modal.model';

export class MtNgxModalConfirm implements MtNgxModal {
    mtNgxModalConfig: MtNgxModalConfirmConfig;
    onActionEvt?: Subject<boolean>;
    onAction?: Observable<any>;
}
