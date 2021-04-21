/**
 * Diretiva de máscara genérica em campo de texto.
 *
 * @author Márcio Casale de Souza <contato@kazale.com>
 * @since 0.0.4
 */

import { Directive, ElementRef, HostListener, Input, Renderer2 } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[kzMask]',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: KzmaskDirective,
    multi: true
  }]
})
export class KzmaskDirective implements ControlValueAccessor {

  onTouched: any;
  onChange: any;
  valor: any;

  @Input() kzMask: string;

  constructor(
    private elementRef: ElementRef,
    private renderer2: Renderer2
  ) {
  }

  setDisabledState(isDisabled: boolean): void {
    this.renderer2.setProperty(this.elementRef.nativeElement, 'disabled', isDisabled);
  }

  writeValue(value: string): void {
    if (value) {
      this.preMask(value);
    } else if (this.onChange != null) {
      this.onChange(null);
      this.elementRef.nativeElement.value = '';
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  @HostListener('keyup', ['$event'])
  onKeyup($event: any) {
    this.preMask($event);
  }

  private preMask($event: any) {
    let valor = $event.target && $event.target.value ? $event.target.value : $event;
    valor = valor ? valor.toString().replace(/\D/g, '') : '';

    // retorna caso pressionado backspace
    if ($event.key === 'Backspace' || $event.keyCode === 8) {
      this.onChange(valor);
      return;
    }

    this.elementRef.nativeElement.value = KzmaskDirective.aplicarMascara(valor, this.kzMask);
    if (this.onChange) {
      this.onChange(KzmaskDirective.aplicarMascara(valor, this.kzMask));
    }
  }

  public static aplicarMascara(valor , mascara: string) {
    valor = valor || '';
    const pad = mascara.replace(/\D/g, '').replace(/9/g, '_');
    const valorMask = valor + pad.substring(0, pad.length - valor.length);

    let valorMaskPos = 0;
    let posCheck = 0;
    valor = '';
    for (let i = 0; i < mascara.length; i++) {
      posCheck = valorMaskPos;
      if (isNaN(parseInt(mascara.charAt(i), 10)) && valorMask[++posCheck]) {
        valor += mascara.charAt(i);
      } else {
        valor += valorMask[valorMaskPos++] || '';
      }
    }

    if (valor.indexOf('_') > -1) {
      valor = valor.substr(0, valor.indexOf('_'));
    }

    return valor;
  }

  static removerMascara(text: string): string {
    return text ? text.replace(/[^0-9]+/g, '') : null;
  }
}
