import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[psapp-collapse-group]',
})
export class CollapseGroupDirective {

  @Input() titulo: string;
  @Input() aberto: boolean;

  constructor(
    public templateRef: TemplateRef<any>
  ) {
  }


}
