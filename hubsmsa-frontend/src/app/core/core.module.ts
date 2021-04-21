import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { NgModule } from '@angular/core';

import { CanActivePublicModule } from '../modules/public/guards/public-guard';
import { AppHttpInterceptor } from './services/app-http-interceptor.service';
import { AuthControlService } from './authentication/auth-control.service';
import { HttpUtilService } from './services/http-util.service';
import { FabricaModalService } from '@core/services/modal-fabrica-util.service';

@NgModule({
  imports: [
    CommonModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      multi: true,
      useClass: AppHttpInterceptor
    },
  ]
})
export class CoreModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: CoreModule,
      providers: [
        HttpUtilService,
        AuthControlService,
        CanActivePublicModule,
        FabricaModalService,
      ],
    };
  }
}
