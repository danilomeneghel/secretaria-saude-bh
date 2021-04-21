import { Pipe, PipeTransform } from '@angular/core';
import { KzmaskDirective } from '@shared/directives/directives/kzmask.directive';

@Pipe({
  name: 'formatText'
})
export class FormatTextPipe implements PipeTransform {

  transform(value: any, formato: any): any {
    return KzmaskDirective.aplicarMascara(value, formato);
  }

}
