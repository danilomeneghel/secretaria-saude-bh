import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'listaFormato'
})
export class ListaFormatoPipe implements PipeTransform {

  transform(value: any): any {
    return  value.map(argumentos => argumentos + '<br>').join('');
  }

}
