import { Inject, Injectable } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import * as $ from 'jquery';
import { filter } from 'rxjs/operators';
import { NgcAlertConfig } from '../models/ngc-alert-config.model';
import { NgcAlertDefaultValues } from '../models/ngc-alert-default.enum';
import { NgcAlertModuleConfig } from '../models/ngc-alert-module-config.mode';
import { NgcAlertType } from '../models/ngc-alert-type.model';
import { NgcAlert } from '../models/ngc-alert.model';
import { NgcContainerByType, NgcContainerTypeEnum } from '../models/ngc-container-type.enum';
import { NgcIconClassByAlertType } from '../models/ngc-icon-class.const';

@Injectable()
export class NgcAlertService {
  cacheAlert: NgcAlert[] = [];
  alertName = 'ngc-alert';
  tagAlertClick = 'ngc-message-click';

  constructor(
    @Inject('config_alert') private readonly config: Partial<NgcAlertModuleConfig>,
    private router: Router
  ) {
    if (!this.config) {
      this.config = {
        alertContainerName: NgcAlertDefaultValues.CONTAINER,
        fechamentoAutomatico: NgcAlertDefaultValues.FECHAMENTO_AUTOMATICO,
        notifyAll: NgcAlertDefaultValues.NOTIFY_All,
        time: NgcAlertDefaultValues.TIME,
        clearAllAfterNotify: NgcAlertDefaultValues.CLEAR_AFTER_NOTIFY,
        hint: NgcAlertDefaultValues.HINT
      };
    }

    this.router.events.pipe(
      filter(event => event instanceof NavigationStart || event instanceof NavigationEnd))
      .subscribe(event => {
        if (event instanceof NavigationStart) {
          this.filterAlertsRendered();
        }
        if (event instanceof NavigationEnd) {
          this.limparCache();
        }
      });
  }

  show(message: string, alertType: NgcAlertType) {
    this.alert({ message, type: alertType });
  }

  /**
   * @description Mostra alerta de sucesso
   * @param message Mensagem a ser exibida no alerta
   * @param config Configurações do alerta
   */
  success(message: string, config?: Partial<NgcAlertConfig>) {
    this.alert({ message, type: NgcAlertType.Success, config });
  }

  /**
   * @description Mostra alerta de erro
   * @param message Mensagem a ser exibida no alerta
   * @param config Configurações do alerta
   */
  error(message: string, config?: Partial<NgcAlertConfig>) {
    this.alert({ message, type: NgcAlertType.Error, config });
  }

  /**
   * @description Mostra alerta de informação
   * @param message Mensagem a ser exibida no alerta
   * @param config Configurações do alerta
   */
  info(message: string, config?: Partial<NgcAlertConfig>) {
    this.alert({ message, type: NgcAlertType.Info, config });
  }

  /**
   * @description Mostra alerta de advertência
   * @param message Mensagem a ser exibida no alerta
   * @param config Configurações do alerta
   */
  warning(message: string, config?: Partial<NgcAlertConfig>) {
    this.alert({ message, type: NgcAlertType.Warning, config });
  }

  /**
   * @description Cria o alerta e o coloca no DOM
   * @param alert Alerta que será exibido
   * @param byCache Alerta está em cache
   */
  private alert(alert: NgcAlert, byCache?: boolean) {
    const modalContainer = this.config.modalAlert || $('.modal-body piweb-container-padrao').length > 0 ? $('.modal-body') : [];
    const containerEncontrado = modalContainer.length > 0 ? modalContainer
      : this.getContainerPadrao(alert.config ? alert.config.specificContainer : null);

    if (this.canAddMessage(alert.message, containerEncontrado)) {

      if (!byCache) {
        this.cacheAlert.push(alert);
      }


      for (let index = 0; index < (this.config.notifyAll ? containerEncontrado.length : 1); index++) {
        const htmlElementContainer = containerEncontrado[index] || $('piweb-body .container');
        const containerType = NgcContainerByType[alert.type];
        const container = $(htmlElementContainer).find('.' + containerType);
        const style = this.getAlertClass(alert.type);
        const containerId = `NGC_CONT_${ index }_${ this.generateHashId() }`;
        const iconClass = NgcIconClassByAlertType[alert.type];
        alert.rendered = false;
        const messageId = `NGC_MESSAGE_${ this.generateHashId() }`;

        if (!alert.config) {
          this.focusOnAlert(htmlElementContainer);
        } else if (!alert.config.noFocus) {
          this.focusOnAlert(htmlElementContainer);

        }

        // Preparar a lista de cliques
        alert.message = alert.message.replaceAll('#click', `name="${ this.tagAlertClick }"`);

        if ((container as []).length === 0) {
          this.clearContainersType(htmlElementContainer);
          container.innerHTML = '';
          const div = document.createElement('div');
          div.innerHTML =
            this.createAlertDiv(containerId, style, containerType, alert, iconClass, messageId);
          htmlElementContainer.prepend(div);

        } else {
          const ancAlert = document.createElement('p');
          ancAlert.setAttribute('name', this.alertName);
          ancAlert.id = messageId;
          ancAlert.innerHTML = alert.message;
          $(ancAlert).appendTo($(container[0]));
        }

        this.setTimeoutOnAlert(containerId, containerType);
        // Avaliar renderização durante uma troca de tela
        setTimeout(() => {
          alert.rendered = true;
        }, 100);

        this.atribuirEventosAoAlerta(messageId, alert.config);
      }
    }

  }

  /**
   * @description Cria a div do alerta que será adicionado ao DOM
   * @param containerId ID da div (container de alertas)
   * @param style Estilos para adicionar ao container
   * @param containerType Tipo do alerta (warning, success...)
   * @param alert Objeto alerta que possui os atributos do alerta
   * @param iconClass Icone atual do alerta
   * @param messageId Identificação do alerta atual
   */
  private createAlertDiv(containerId: string, style, containerType, alert: NgcAlert, iconClass, messageId: string) {
    return `<div id="${ containerId }" style="font-size: 16px;text-align: left;"
                    class="alert ${ style } ${ containerType }" style="font-weight: bold;">
              <a class="close cursor-pointer" data-dismiss="alert"
                    title="${ alert.config && alert.config.hintClose ? alert.config.hintClose : this.config.hint || '' }">×</a>
              <i style="font-size: 1.50em;" class="${ iconClass }"></i>
              <p id="${ messageId }" name="${ this.alertName }">${ alert.message }</p>
            </div>`;
  }

  atribuirEventosAoAlerta(messageId: string, alertConfig: Partial<NgcAlertConfig>) {
    if (alertConfig && alertConfig.listClickEvents) {
      const elements = $(`#${ messageId }`).find(`[name="${ this.tagAlertClick }"]`);
      alertConfig.listClickEvents.forEach((fun, index) => {
        $(elements[index]).click(e => fun(e.originalEvent));
      });
    }

  }

  /**
   * @description Verifica se existe mensagem e se ela é a mais recente
   * @param message Mensagem que será mostrada no alerta
   */
  private canAddMessage(message: string, container: any) {
    const ngcAlert = $(container).find(`*[name="${this.alertName}"`);
    const lastNgcAlert = ngcAlert[ngcAlert.length - 1];

    return message && !(lastNgcAlert && lastNgcAlert.innerText === message);
  }

  private focusOnAlert(htmlElementContainer): any {
    $('html, body').animate({
      scrollTop: $(htmlElementContainer).offset().top - 100
    }, 20);
  }

  private filterAlertsRendered(): any {
    this.cacheAlert = this.cacheAlert.filter(alert => !alert.rendered);
  }

  private clearContainersType(container: any): any {
    Object.keys(NgcContainerTypeEnum).forEach(te => {
      const htmlContainer = $(container).find('.' + NgcContainerTypeEnum[te]);
      $(htmlContainer).remove();
    });
  }


  private generateHashId(): string {
    let text = '';
    const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    return text;
  }

  private setTimeoutOnAlert(divId: string, containerType: NgcContainerTypeEnum) {
    if ((containerType === NgcContainerTypeEnum.SUCCESS
      || containerType === NgcContainerTypeEnum.INFO) && this.config.fechamentoAutomatico) {
      setTimeout(() => {
        $('#' + divId).fadeOut('slow', () => {
          document.getElementById(divId).remove();
        });
      }, this.config.time);
    }
  }

  private getAlertClass(type: NgcAlertType): any {
    switch (type) {
      case NgcAlertType.Success:
        return 'alert-success';
      case NgcAlertType.Error:
        return 'alert-danger';
      case NgcAlertType.Info:
        return 'alert-info';
      case NgcAlertType.Warning:
        return 'alert-warning';
    }
  }


  private limparCache() {
    if (this.cacheAlert.length === 0) {
      return;
    } else {
      // tslint:disable-next-line: prefer-const
      let lista = [];
      Object.assign(lista, this.cacheAlert);
      // tslint:disable-next-line:prefer-for-of
      for (let i = 0; i < lista.length; i++) {
        setTimeout(() => {
          this.alert(lista.pop(), true);
        });
      }
      this.cacheAlert = [];
    }
  }

  clearAll(): void {
    this.clearContainersType(this.getContainerPadrao());
  }

  private getContainerPadrao(specContainer?: string) {
    const nomeContainer = specContainer ? specContainer : this.config.alertContainerName;
    return $('.' + nomeContainer) || [];
  }

}

String.prototype.replaceAll = function(search, replacement) {
  const target = this;
  return target.replace(new RegExp(search, 'g'), replacement);
};

declare global {
  interface String {
    replaceAll: any;
  }
}
