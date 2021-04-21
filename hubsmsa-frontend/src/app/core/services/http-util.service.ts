import { Injectable } from '@angular/core';

@Injectable()
export class HttpUtilService {

  /**
   * Metodo para mapear um objeto qualquer para um HttpParams, com base nos atributos.
   * @param objeto Objeto para mapeamento
   */
  httpParamsByObjeto(objeto: object): {
    [param: string]: string | string[]
  } {
    const httpParams: {
      [param: string]: string | string[];
    } = {};
    if (objeto != null) {
      // tslint:disable-next-line:forin
      for (const key in objeto) {
        const parametro = objeto[key];
        if (parametro !== null && parametro !== 'null' && parametro !== 'undefined' && parametro !== undefined) {
          if (String(parametro).trim()) {
            httpParams[key] = String(objeto[key]);
          }
        }
      }
    }
    return httpParams;
  }

  objToUrl(queryTransform: any) {
    if (queryTransform) {
      const newForm = Object.keys(queryTransform).filter(item => queryTransform[item] !== null);
      return '?' + newForm.map(item => {
        if (queryTransform[item] instanceof Array) {
          return queryTransform[item].map(arr => item + '=' + arr).join('&');
        }
        return item + '=' + queryTransform[item];
      }).join('&');
    } else {
      return '';
    }
  }

  /**
   * @description Faz o Download do arquivo blob
   * @param dados Dados que terão o arquivo
   * @param tipo Tipo do arquivo
   */
  baixarArquivo(dados: any, tipo: string) {
    const blob = new Blob([dados.body], { type: tipo });
    const linkElement = document.createElement('a');
    const url = window.URL.createObjectURL(blob);

    linkElement.setAttribute('download', dados.headers.get('Content-Disposition').split('=')[1]);
    linkElement.setAttribute('href', url);
    document.body.appendChild(linkElement);
    linkElement.click();
  }
}
