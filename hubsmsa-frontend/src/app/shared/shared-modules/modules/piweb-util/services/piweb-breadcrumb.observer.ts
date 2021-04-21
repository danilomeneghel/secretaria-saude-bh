import { Subject } from 'rxjs';

export class PiwebBreadcrumbObserver {

    private listarRotasEvt: Subject<any> = new Subject();

    $listarRotas = this.listarRotasEvt.asObservable();

    listarRotas() {
        this.listarRotasEvt.next();
    }
}
