import { Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
  name: 'formatarData'
})
export class FormatarDataPipe implements PipeTransform {
  constructor(private datePipe: DatePipe) {
  }

  transform(value: string, args?: any): any {
    const dateFormat = /\d{4}[ -]\d{2}[ -]\d{2}T\d{2}:\d{2}:\d{2}.\d{3}/;
    return dateFormat.test(value) ? this.datePipe.transform(value, args) : value;
  }

}
