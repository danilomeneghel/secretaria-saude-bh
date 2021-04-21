import { Directive, ElementRef, forwardRef, HostListener, Input, OnInit, Optional } from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DatePipe } from '@angular/common';

const CUSTOM_INPUT_DATE_CONTROL_VALUE_ACCESSOR: any = {
  provide: NG_VALUE_ACCESSOR,
  useExisting: forwardRef(() => PiwebDataDirective),
  multi: true
};

@Directive({
  selector: '[piweb-data]',
  providers: [CUSTOM_INPUT_DATE_CONTROL_VALUE_ACCESSOR]
})
export class PiwebDataDirective implements ControlValueAccessor, OnInit {
  onChange: any;
  onTouched: any;
  isDisabled: boolean;
  formControl: FormControl;
  hasFirstValue = false;

  @Input() formControlName: string;

  @Input() set maxDate(value: string) {
    this.element.nativeElement.setAttribute('max', value || '9999-12-31');
  }

  @Input() set minDate(value: string) {
    this.element.nativeElement.setAttribute('min', value || '1900-01-01');
  }

  constructor(
    private element: ElementRef,
    private datePipe: DatePipe,
    @Optional()
    private controlContainer: ControlContainer,
  ) {
    this.definiraAtributoTipoData();
    this.definirEstiloPiweb();
  }

  ngOnInit(): void {
    this.formControl = this.controlContainer.control.get(this.formControlName) as FormControl;
  }

  /**
   * Verifica de a data esta no formato dd/MM/yyyy
   * Se estiver, tranforma para o formato yyyy-MM-dd apenas no visual (no campo HTML)
   * No formulario aplica o valor original
   * @param value Valor da data
   */
  writeValue(value: string): void {
    const regxDate = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/i;

    setTimeout(() => { // Necessario para aguardar a criacao do OnChange
      if (value && regxDate.test(value)) {
        const dateUs = this.dataBrParaUs(value);
        this.registrarValor(value);
        this.element.nativeElement.value = dateUs;
      } else if (this.onChange) {
        this.registrarValor(value);
        this.element.nativeElement.value = value;
      }

      this.hasFirstValue = true;
    });
  }

  private registrarValor(valor: any) {
    if (this.onChange) {
      this.onChange(valor);

      if (!this.hasFirstValue && this.formControl) {
        this.formControl.markAsPristine();
      }
    }
  }

  /**
   * Tranforma a data para o formato americano (
   * @param value Valor de data a ser transformado
   * @return data no formato yyyy-MM-dd
   */
  private dataBrParaUs(value: string): string {
    const dataArr = value.split('/');
    const dataUsBarra = `${ dataArr[1] }/${ dataArr[0] }/${ dataArr[2] }`;
    return this.datePipe.transform(dataUsBarra, 'yyyy-MM-dd');
  }

  @HostListener('change', ['$event'])
  onChangeEvent(event: any) {
    const formatedDate = this.datePipe.transform(event.target.value, 'dd/MM/yyyy');
    this.registrarValor(formatedDate);
  }

  /**
   * @description Adiciona divs e atributos necessários para transformar o campo input num campo data do PIWEB
   */
  private definirEstiloPiweb() {
    if (!this.element.nativeElement.parentElement.classList.contains('input-group')) {
      const newInputGroupElement = document.createElement('div');
      const elementParent = this.element.nativeElement.parentNode;

      newInputGroupElement.classList.add('input-group');
      elementParent.replaceChild(newInputGroupElement, this.element.nativeElement);
      newInputGroupElement.appendChild(this.element.nativeElement);
    }

    this.criarIconeData();
  }

  /**
   * Adiciona icone de data ao input
   */
  private criarIconeData() {
    const groupElement = document.createElement('div');
    const spanElement = document.createElement('span');
    const iconElement = document.createElement('i');
    const elementParent = this.element.nativeElement.parentNode;

    groupElement.classList.add('input-group-prepend');
    spanElement.classList.add('input-group-text');
    iconElement.classList.add('far');
    iconElement.classList.add('fa-calendar-alt');

    spanElement.append(iconElement);
    groupElement.append(spanElement);
    elementParent.appendChild(groupElement);
    this.element.nativeElement.setAttribute('max', '9999-12-31');
  }

  /**
   * Adiciona o atributo "type" com valor "date" no campo
   */
  definiraAtributoTipoData() {
    if (this.element.nativeElement.getAttribute('type') !== 'date') {
      this.element.nativeElement.setAttribute('type', 'date');
    }
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
      this.element.nativeElement.setAttribute('disabled', 'true');
    } else {
      this.element.nativeElement.removeAttribute('disabled');
    }
  }

}
