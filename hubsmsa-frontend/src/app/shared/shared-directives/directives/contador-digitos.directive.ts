import { AfterViewInit, Directive, ElementRef, Input, OnDestroy } from '@angular/core';
import { NgControl } from '@angular/forms';
import { Subscription } from 'rxjs';

@Directive({
  selector: '[psappContadorMaxlength]'
})
export class ContadorMaxlengthDirective implements AfterViewInit, OnDestroy {
  private subscription: Subscription;

  @Input('psappContadorMaxlength') tamanho: number;

  private span: HTMLElement;

  constructor(private element: ElementRef, private control: NgControl) {

  }

  ngAfterViewInit(): void {
    this.verificarAtributos();

    this.construirSpan();

    if (this.control && this.element.nativeElement.tagName === "TEXTAREA") {
      this.calcularDigitos();
      this.subscription = this.control.valueChanges.subscribe(() => {
        this.calcularDigitos();
      });
    }
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  construirSpan(): any {
    const small = document.createElement('small');
    small.innerHTML = `Máximo de caracteres: `;
    small.className = "form-text text-muted";
    const span = document.createElement('span');
    span.innerHTML = String(this.recuperarTamanho());
    small.appendChild(span);
    this.span = span;
    this.element.nativeElement.parentElement.appendChild(small)
  }

  recuperarTamanho(): number {
    return (this.element.nativeElement.maxLength ? this.element.nativeElement.maxLength : this.element.nativeElement.maxLength = this.tamanho);
  }

  verificarAtributos(): any {
    if (this.tamanho) {
      this.element.nativeElement.maxLength = this.tamanho;
    }
  }

  private calcularDigitos(): any {
    const tamanhoAtual = (this.element.nativeElement.value).length;
    this.span.textContent = String((this.recuperarTamanho() - tamanhoAtual));
  }
}
