import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[piweb-btn-remover]'
})
export class PiwebBtnRemoverDirective {

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();
  }

  atribuirClass() {
    this.element.nativeElement.className += ' btn btn-link btn-sm text-danger btn-remover';

    let icon = (document.createElement('i') as HTMLElement);
    icon.className += ' fa fa-trash-alt';

    this.element.nativeElement.appendChild(icon);

  }

}
