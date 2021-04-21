import { Directive, ElementRef, HostListener } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Directive({
  selector: '[apenasNumero]',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: ApenasNumeroDirective,
    multi: true
  }]
})
export class ApenasNumeroDirective implements ControlValueAccessor {

  onTouched: any;
  onChange: any;
  valor: any;
  isDisabled: boolean;

  constructor(private _elemento: ElementRef) {
  }

  writeValue(value: string): void {
    const newValue = value ? this.apenasNumeros(value) : value;
    this.registrarValor(newValue);
  }

  private registrarValor(valor: any) {
    if (this.onChange) {
      this.onChange(valor);
    }
    this._elemento.nativeElement.value = valor;
  }

  @HostListener('input', ['$event']) onInputChange(event) {
    const valorInicial = this._elemento.nativeElement.value;
    this.registrarValor(this.apenasNumeros(valorInicial, event));
  }

  apenasNumeros(valor: string, event?): string {
    const novoValor = valor.replace(/\D/g, '');

    if (event && valor !== this._elemento.nativeElement.value) {
      event.stopPropagation();
    }

    this.registrarValor(novoValor);
    return novoValor;
  }


  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.isDisabled = isDisabled;

    if (isDisabled) {
      this._elemento.nativeElement.setAttribute('disabled', 'true');
    } else {
      this._elemento.nativeElement.removeAttribute('disabled');
    }
  }

}
