import { Component, Input, OnInit, ElementRef } from '@angular/core';
/**
 * @author Sérgio Davi <sergio.teotonio@ctis.com.br>
 * @deprecated Utilizar a diretriva [piweb-btn-primario]
 */
@Component({
  selector: 'piweb-botao-primario',
  templateUrl: './piweb-botao-primario.component.html',
  styleUrls: ['./piweb-botao-primario.component.css']
})
export class PiwebBotaoPrimarioComponent implements OnInit {
  classeBtn = 'btn btn-success';

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
