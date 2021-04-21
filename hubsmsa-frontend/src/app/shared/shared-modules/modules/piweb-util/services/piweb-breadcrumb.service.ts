import { Injectable } from '@angular/core';
import { PiwebBreadcrumbObserver } from './piweb-breadcrumb.observer';

@Injectable()
export class PiwebBreadcrumbService {

    constructor(private breadCrumbObs: PiwebBreadcrumbObserver) { }

    listarRotas() {
        this.breadCrumbObs.listarRotas();
    }
}
