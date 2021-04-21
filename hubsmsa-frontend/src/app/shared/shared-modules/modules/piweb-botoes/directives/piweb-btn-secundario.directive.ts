import { AfterViewInit, Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[piweb-btn-secundario]'
})
export class PiwebBtnSecundarioDirective implements AfterViewInit {

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();

  }
  atribuirClass() {
    this.element.nativeElement.classList.add('btn');
    this.element.nativeElement.classList.add('btn-outline-secondary');
  }
  ngAfterViewInit(): void {
    this.element.nativeElement.title = this.element.nativeElement.textContent.toUpperCase();
  }

}