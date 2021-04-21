import { Component, EventEmitter, forwardRef, Output, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { SelecionarArquivoModel } from '@shared/modules/modules/piweb-inputs/models/selecionar-arquivo-model';
import { environment } from '../../../../../../../environments/environment';

@Component({
  selector: 'piweb-selecionar-arquivo',
  templateUrl: './piweb-selecionar-arquivo.component.html',
  styleUrls: ['./piweb-selecionar-arquivo.component.css'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PiwebSelecionarArquivoComponent),
      multi: true
    }
  ]
})
export class PiwebSelecionarArquivoComponent implements ControlValueAccessor {
  @Output() arquivoSelecionado: EventEmitter<SelecionarArquivoModel> = new EventEmitter();
  arquivo: SelecionarArquivoModel = new SelecionarArquivoModel();

  onChange: any;
  onTouched: any;
  disabled: boolean;
  @Input() selecionarPorTipo: string;
  @Input() mostrarLixeira = false;

  constructor() {
  }

  fileInput(event: any) {
    const file: File = event.target.files[0];
    const reader: FileReader = new FileReader();

    this.limparArquivo();

    reader.onloadend = () => {
      this.setArquivo({
        hashArquivo: reader.result,
        nomeArquivo: file.name,
        id: null
      });

    };

    if (file) {
      reader.readAsDataURL(file);
    }

  }

  setArquivo(valor: SelecionarArquivoModel) {
    if (valor) {
      this.arquivo = valor;
      this.arquivoSelecionado.emit(valor);
    }
  }

  limparArquivo() {
    this.setArquivo({
      hashArquivo: null,
      nomeArquivo: null,
      id: null
    });
  }

  removerValorElemento(elemento: HTMLInputElement) {
    if (this.selecionarArquivoEnabled()) {
      elemento.value = null;
      elemento.dispatchEvent(new Event('change'));
      this.limparArquivo();
    }
  }


  selecionarArquivoEnabled() {
    return !document.getElementById('file').hasAttribute('disabled');
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  writeValue(nomeArquivo): void {
    if (typeof nomeArquivo === 'string') {
      this.arquivo.nomeArquivo = nomeArquivo;
    }
  }

  baixarArquivo(id: number) {
    window.open(`${ environment.apiUrl }/arquivos/${ id }`);
  }
}
