import { Component, ElementRef, Input, OnInit } from '@angular/core';
/**
 * @author Sergio Davi <sergio.teotonio@ctis.com.br>
 * @deprecated Utilizar a diretriva [piweb-btn-secundario]
 */
@Component({
  selector: 'piweb-botao-secundario',
  templateUrl: './piweb-botao-secundario.component.html',
  styleUrls: ['./piweb-botao-secundario.component.css']
})
export class PiwebBotaoSecundarioComponent implements OnInit {

  classeBtn = 'btn  btn-outline-secondary';

  @Input() btnTitulo: string;

  @Input() disabled: boolean;

  constructor(private elt: ElementRef) {
  }

  ngOnInit() {
    if (!this.btnTitulo) {
      this.btnTitulo = this.elt.nativeElement.textContent;
    }
  }

}
