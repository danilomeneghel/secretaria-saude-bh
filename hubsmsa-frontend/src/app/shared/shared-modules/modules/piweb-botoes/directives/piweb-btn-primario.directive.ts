import { Directive, ElementRef, AfterViewInit } from '@angular/core';

@Directive({
  selector: '[piweb-btn-primario]'
})
export class PiwebBtnPrimarioDirective implements AfterViewInit {

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();

  }
  atribuirClass() {
    this.element.nativeElement.classList.add('btn');
    this.element.nativeElement.classList.add('btn-success');
  }
  ngAfterViewInit(): void {
    this.element.nativeElement.title = this.element.nativeElement.textContent.toUpperCase();
  }

}
