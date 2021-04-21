import { NgcAlertType } from './ngc-alert-type.model';

export const NgcIconClassByAlertType = {
    [NgcAlertType.Success]: 'fa fa-check-circle',
    [NgcAlertType.Error]: 'fa fa-exclamation-circle',
    [NgcAlertType.Warning]: 'fa fa-exclamation-triangle',
    [NgcAlertType.Info]: 'fa fa-info-circle'
};

