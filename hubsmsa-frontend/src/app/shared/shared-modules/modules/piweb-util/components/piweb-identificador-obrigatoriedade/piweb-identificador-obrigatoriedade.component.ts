import { AfterViewInit, ChangeDetectorRef, Component, ViewChild } from '@angular/core';

@Component({
  selector: 'piweb-identificador-obrigatoriedade',
  templateUrl: './piweb-identificador-obrigatoriedade.component.html',
  styleUrls: ['./piweb-identificador-obrigatoriedade.component.css']
})
export class PiwebIdentificadorObrigatoriedadeComponent implements AfterViewInit {
  @ViewChild('textContent') textContent;
  mostrarMensagemPadrao: boolean;

  constructor(private cdRef: ChangeDetectorRef) {
  }

  ngAfterViewInit(): void {
    this.mostrarMensagemPadrao = this.textContent.nativeElement && this.textContent.nativeElement.childNodes.length > 0;
    this.cdRef.detectChanges();

  }

}
