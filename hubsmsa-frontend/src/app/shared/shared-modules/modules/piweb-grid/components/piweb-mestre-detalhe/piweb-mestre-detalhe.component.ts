import { Component, Input } from '@angular/core';

@Component({
  selector: 'piweb-mestre-detalhe',
  templateUrl: './piweb-mestre-detalhe.component.html',
  styleUrls: ['./piweb-mestre-detalhe.component.css'],
})
export class PiwebMestreDetalheComponent {
  @Input() titulo: string;

  collapseId = this.generateHashId();
  collapsed: boolean;

  private generateHashId(): string {
    let text = '';
    const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'; // Nao pode haver numeros
    for (let i = 0; i < 5; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
  }

}
