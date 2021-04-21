import { Injectable } from '@angular/core';

/**
 * @author Sergio Davi <sergio.teotonio@ctis.com.br>
 */ 
@Injectable()
export class PiwebUtilService {

    constructor() { }

    generateHashId(size = 5): string {
        let text = '';
        const possible = 'ABSCDEFHJRTJGMTYUOUILASDSF';
        if (size > possible.length) {
            size = possible.length + 1;
        }

        for (let i = 0; i < size; i++) {
            text += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        return text;
    }

}
