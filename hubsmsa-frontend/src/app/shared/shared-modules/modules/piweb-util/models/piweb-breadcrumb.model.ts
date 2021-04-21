import { Params } from '@angular/router';

export interface PiwebBreadCrumbModel {
    titulo: string;
    link: string;
    params?: Params;
    ativarLink?: boolean;
}
