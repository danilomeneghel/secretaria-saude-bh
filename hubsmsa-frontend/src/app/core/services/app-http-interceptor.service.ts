import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import HttpStatusCode from '@shared/models/enum/http-status.enum';
import { MensagemEnum } from '@shared/models/enum/mensagem.enum';
import { TipoAppResponseEnum } from '@shared/models/enum/tipo-app-response.enum';
import { MensagemPadraoModel } from '@shared/models/interface/mensagem-padrao.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AppResponsePadraoModel } from '@shared/models/interface/app-response-padrao.model';
import { AppUtilService } from '@core/services/app-util.service';

export class AppHttpInterceptor implements HttpInterceptor {
  static exibindoSpinner = false;

  constructor(
    private toastrService: NgcAlertService,
    private loadingService: NgxSpinnerService,
  ) {
    this.loadingService.spinnerObservable.subscribe(show => {
      AppHttpInterceptor.exibindoSpinner = show;
    });
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!AppHttpInterceptor.exibindoSpinner) {
      this.loadingService.show();
    }

    const STATUS_INDEFINIDO = 0;

    return next.handle(req).pipe(tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          this.loadingService.hide();
          if (event.body.payload) {
            this.exibirMensagem((event.body as AppResponsePadraoModel).mensagem);
          } else {
            this.exibirMensagem(event.body as MensagemPadraoModel);
          }
        }
      },
      (response) => {
        if (response.status === STATUS_INDEFINIDO) {
          this.toastrService.error(MensagemEnum.SERVICO_INDISPONIVEL);
        } else if (response.error !== undefined) {
          switch (response.status) {
            case HttpStatusCode.BAD_REQUEST:
              let valorResponse = response.error;
              const tipoResponse = typeof valorResponse;

              try {
                if (tipoResponse === 'string') {
                  valorResponse = JSON.parse(valorResponse);
                }

                // Se o erro for referente a uma requisicao de arquivo
                if (valorResponse.constructor === ArrayBuffer) {
                  valorResponse = AppUtilService.arrayBufferToJson(valorResponse);
                }

                (valorResponse as MensagemPadraoModel[]).forEach(appErrorModel => this.exibirMensagem(appErrorModel));

              } catch (error) {
                this.toastrService.error(MensagemEnum.ERRO_AO_REALIZAR_PARSE_JSON);
              }
              break;

            case HttpStatusCode.NOT_FOUND:
              this.toastrService.error(MensagemEnum.SERVICO_NAO_EXISTE);
              console.error(MensagemEnum.ERRO_NO_SERVICO.replace('{VALUE}', (response as HttpResponse<any>).url));
              break;

            case HttpStatusCode.METHOD_NOT_ALLOWED:
              this.toastrService.error(MensagemEnum.SERVICO_NAO_EXISTE);
              console.error(MensagemEnum.ERRO_NO_SERVICO.replace('{VALUE}', (response as HttpResponse<any>).url));
              break;

            case HttpStatusCode.INTERNAL_SERVER_ERROR:
              this.toastrService.error(MensagemEnum.ERRO_INTERNO);
              console.error(MensagemEnum.ERRO_NO_SERVICO.replace('{VALUE}', (response as HttpResponse<any>).url));
              break;

            default:
              this.toastrService.error(MensagemEnum.ERRO_DESCONHECIDO);
              break;
          }
        }
        this.loadingService.hide();
      }));
  }

  private exibirMensagem(erroModel: MensagemPadraoModel) {
    try {
      if (erroModel) {
        if (erroModel.type != null) {
          if (erroModel.type === TipoAppResponseEnum.ERROR) {
            this.toastrService.error(erroModel.msg);
          } else if (erroModel.type === TipoAppResponseEnum.SUCCESS) {
            this.toastrService.success(erroModel.msg);
          }
        }
      }
    } catch (error) {
      this.toastrService.error(MensagemEnum.ERRO_AO_FORMATAR_MENSAGEM);
    }
  }
}
