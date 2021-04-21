import { Directive, ElementRef, Input, AfterViewInit } from '@angular/core';

@Directive({
  selector: '[piweb-tabela-botao]'
})
export class PiwebTabelaBotaoDirective implements AfterViewInit {

  @Input() icone: string;

  constructor(
    private element: ElementRef
  ) {
    this.atribuirClass();

  }
  atribuirClass() {
    const listaClasses = 'btn btn-link btn-sm icone';
    listaClasses.split(' ').forEach(classe => {
      this.element.nativeElement.classList.add(classe);
    });
    const span = document.createElement('span');
    span.innerHTML = this.element.nativeElement.title || '';
    span.className = 'sr-only';
    this.element.nativeElement.prepend(span);

  }
  ngAfterViewInit(): void {
    // Icone
    if (this.icone) {
      const icone = document.createElement('i');
      icone.className = this.icone || '';
      this.element.nativeElement.append(icone);
    }
  }


}
