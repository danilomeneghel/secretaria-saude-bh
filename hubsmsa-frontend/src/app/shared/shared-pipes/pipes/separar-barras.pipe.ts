import { Pipe, PipeTransform } from '@angular/core';
@Pipe({
  name: 'separarBarras'
})
export class SepararBarrasPipe implements PipeTransform {
  transform(value: any, index: number, size: number): any {
    if (!!index && index < (size - 1)) {
      return `${value} | `
    } else if (index === size - 1) {
      return value;
    }
  }
}