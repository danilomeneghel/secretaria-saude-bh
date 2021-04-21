import { Component, Input } from '@angular/core';
@Component({
  selector: 'piweb-header',
  templateUrl: './piweb-header.component.html'
})
export class PiwebHeaderComponent  {
  @Input() tituloAplicacao: string;
  @Input() minTituloAplicacao: string;

  constructor() { }

 
}
