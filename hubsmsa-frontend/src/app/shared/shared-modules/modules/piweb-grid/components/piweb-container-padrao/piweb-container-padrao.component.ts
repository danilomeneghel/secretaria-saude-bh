import { Component, Input, OnInit } from '@angular/core';

/**
 * Container Padrão
 * @author Sérgio Davi <sergio.teotonio@ctis.com.br>
 */
@Component({
  selector: 'piweb-container-padrao',
  templateUrl: './piweb-container-padrao.component.html',
  styleUrls: ['./piweb-container-padrao.component.css']
})
export class PiwebContainerPadraoComponent implements OnInit {

  @Input() containerCadastro: boolean;

  @Input() titulo: string;

  @Input() subtitulo: string;

  constructor() {
  }

  ngOnInit() {
  }

}
