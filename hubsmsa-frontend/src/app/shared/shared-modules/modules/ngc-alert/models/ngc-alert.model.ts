import { NgcAlertType } from './ngc-alert-type.model';
import { NgcAlertConfig } from './ngc-alert-config.model';


export class NgcAlert {
    type: NgcAlertType;
    message: string;
    config?: Partial<NgcAlertConfig>;
    rendered ?= false;
}
