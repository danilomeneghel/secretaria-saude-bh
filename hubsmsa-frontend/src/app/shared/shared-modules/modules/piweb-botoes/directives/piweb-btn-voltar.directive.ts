import { AfterViewInit, Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[piweb-btn-voltar]'
})
export class PiwebBtnVoltarDirective implements AfterViewInit {

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();

  }

  atribuirClass() {
    this.element.nativeElement.classList.add('btn-outline-secondary');
    this.element.nativeElement.classList.add('voltar');

    let icon = (document.createElement('i') as HTMLElement);
    icon.classList.add('fas');
    icon.classList.add('fa-arrow-left');
    icon.style.marginRight = '0.35rem';

    this.element.nativeElement.appendChild(icon);
  }

  ngAfterViewInit(): void {
    this.element.nativeElement.title = this.element.nativeElement.textContent.toUpperCase();
  }

}
