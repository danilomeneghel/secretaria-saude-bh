import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[piweb-btn-editar]'
})
export class PiwebBtnEditarDirective {

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();
  }

  atribuirClass() {
    this.element.nativeElement.className += ' btn btn-link btn-sm text-primary btn-editar';

    let icon = (document.createElement('i') as HTMLElement);
    icon.className += ' fa fa-pencil-alt';

    this.element.nativeElement.appendChild(icon);

  }

}
