import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[piwebColuna]'
})
export class PiwebColunaDirective {

  @Input() tituloColuna: string;

  @Input('piwebColuna') atributo: string;

  @Input() class: string;

  @Input() bodyClass: string;

  @Input() esconderOrdenacao: boolean;

  constructor(
    public mTemplate: TemplateRef<any>
  ) {
  }


}
