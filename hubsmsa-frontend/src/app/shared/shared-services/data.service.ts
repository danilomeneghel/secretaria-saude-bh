import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {

  private bsEmpresa = new BehaviorSubject<number>(null);
  private empresaSource = this.bsEmpresa.asObservable();

  setSharedEmpresa(message: number) {
    this.bsEmpresa.next(message);
  }

  getSharedEmpresa(): Observable<number> {
    return this.empresaSource;
  }

}
