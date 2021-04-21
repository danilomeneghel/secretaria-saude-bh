import { HttpErrorResponse } from '@angular/common/http';
import { ErrorHandler, Inject, Injectable, Injector } from '@angular/core';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { KeycloakService } from 'keycloak-angular';
import { AppUtilService } from '@core/services/app-util.service';

@Injectable()
export class AppErrorHandler extends ErrorHandler {
  constructor(@Inject(Injector) private injector: Injector) {
    super();
  }

  private get toastrService(): NgcAlertService {
    return this.injector.get(NgcAlertService);
  }


  handleError(errorResponse: HttpErrorResponse | any) {
    try {
      let valorResponse = errorResponse.error;
      // Se o erro for referente a uma requisicao de arquivo
      if (valorResponse.constructor === ArrayBuffer) {
        valorResponse = AppUtilService.arrayBufferToJson(valorResponse);
      }

      if (errorResponse.status === 400) {
        valorResponse.forEach(appErro => {
          if (appErro.msg && appErro.msg.length > 0) {
            this.toastrService.error(appErro.msg);
          }
        });
      }
      switch (errorResponse.status) {
        case 401:
          this.toastrService.warning(MensagemEnum.A004);
          this.injector.get(KeycloakService).logout();
          break;
        case 403:
          this.toastrService.warning(MensagemEnum.A002);
          break;
        case 404:
          this.toastrService.error(MensagemEnum.E003);
          break;
        case 500:
          this.toastrService.error(MensagemEnum.E004);
          break;
      }
    } catch (error) {

      this.toastrService.error(MensagemEnum.E005);

    }
    super.handleError(errorResponse);
  }
}
