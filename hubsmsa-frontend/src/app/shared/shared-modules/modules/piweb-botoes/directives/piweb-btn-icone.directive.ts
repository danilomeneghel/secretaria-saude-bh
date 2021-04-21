import { AfterViewInit, Directive, ElementRef, Input } from '@angular/core';

@Directive({
  selector: '[piweb-btn-icone]'
})
export class PiwebBtnIconeDirective implements AfterViewInit {
  @Input('piweb-btn-icone') icone: string;

  constructor(private element: ElementRef) {
  }

  private definirIcone() {
    this.element.nativeElement.className += ' btn btn-link';

    const icon = (document.createElement('i') as HTMLElement);

    // Verifica se ha algum tipo 'fa' precedendo a classe que especifica o icone, se sim, nao adiciona a classe padrao 'fa'
    if (containsMultiple(this.icone.split(' '), ['fa', 'fas', 'far'])) {
      icon.className += this.icone;
    } else {
      icon.classList.add('fa');
      icon.classList.add(this.icone);
    }

    this.element.nativeElement.appendChild(icon);

  }


  ngAfterViewInit(): void {
    this.definirIcone();
  }
}

export function containsMultiple(array: Array<any>, items: Array<any>) {
  return items.some(item => array.indexOf(item) > -1);

}
