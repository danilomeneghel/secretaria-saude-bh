import { NgcAlertType } from './ngc-alert-type.model';

export enum NgcContainerTypeEnum {
    SUCCESS = 'ngc-success-container',
    ERROR = 'ngc-error-container',
    WARNING = 'ngc-warn-container',
    INFO = 'ngc-info-container',
}


export const NgcContainerByType = {
    [NgcAlertType.Success]: NgcContainerTypeEnum.SUCCESS,
    [NgcAlertType.Error]: NgcContainerTypeEnum.ERROR,
    [NgcAlertType.Warning]: NgcContainerTypeEnum.WARNING,
    [NgcAlertType.Info]: NgcContainerTypeEnum.INFO
};
