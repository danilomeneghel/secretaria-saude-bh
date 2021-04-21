import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'enumTradutor'
})
export class EnumTradutorPipe implements PipeTransform {

  transform(value: string, enumMsgParaTraduzir: object): any {
    return enumMsgParaTraduzir[value];
  }

}
