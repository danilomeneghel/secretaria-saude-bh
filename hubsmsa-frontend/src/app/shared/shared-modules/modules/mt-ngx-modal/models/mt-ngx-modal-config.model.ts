import { ModalOptions } from 'ngx-bootstrap';

export interface MtNgxModalConfig {
    id?: string;
    data?: any;
    ngxModalOptions?: ModalOptions;
    template: any;
    closeOnAnyBtnClick?: boolean;
}
