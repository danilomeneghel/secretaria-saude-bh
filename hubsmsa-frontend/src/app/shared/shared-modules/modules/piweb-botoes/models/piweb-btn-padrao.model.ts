import { OnInit, ElementRef, Input } from '@angular/core';

export abstract class PiwebBotaoPadrao implements OnInit {

    @Input() btnTitulo: string;

    @Input() disabled: boolean;

    constructor(protected elt: ElementRef) {
    }

    ngOnInit() {
        if (!this.btnTitulo) {
            this.btnTitulo = this.elt.nativeElement.textContent;
        }
    }
}