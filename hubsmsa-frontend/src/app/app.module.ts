import { HttpClientModule } from '@angular/common/http';
import { APP_INITIALIZER, ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MtNgxModalModule } from '@shared/modules/modules/mt-ngx-modal/mt-ngx-modal.module';
import { NgcAlertModule } from '@shared/modules/modules/ngc-alert/ngc-alert.module';
import { PiwebCoreModule } from '@shared/modules/modules/piweb-core/piweb-core.module';
import { ValidacaoFormularioModule } from '@shared/modules/modules/validacao-formulario/validacao-formulario.module';
import { NgxSpinnerModule } from 'ngx-spinner';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from '@core/core.module';
import { PrivateModule } from './modules/private/private.module';
import { PublicModule } from './modules/public/public.module';
import { SharedComponentsModule } from '@shared/components/shared-components.module';
import { SharedModalModule } from './shared/shared-modal/shared-modal.module';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializer } from './app-init';
import { AppErrorHandler } from './app-error-handler';
import { FmTiposDeParaModule } from './modules/private/modules/fm-tipos-de-para/fm-tipos-de-para.module';
import { UsuarioService } from './shared/shared-services/usuario.service';


@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        NgxSpinnerModule,
        NgcAlertModule.forRoot({
            alertContainerName: 'ngc-alert-container',
            fechamentoAutomatico: false,
        }),
        ValidacaoFormularioModule.forRoot(),
        SharedComponentsModule,
        PrivateModule,
        PublicModule,
        CoreModule.forRoot(),
        MtNgxModalModule,
        PiwebCoreModule,
        SharedModalModule,
        KeycloakAngularModule,
        FmTiposDeParaModule
    ],
    providers: [
        {
            provide: ErrorHandler,
            useClass: AppErrorHandler
        },
        {
            provide: APP_INITIALIZER,
            useFactory: initializer,
            multi: true,
            deps: [KeycloakService, UsuarioService]
        },
        UsuarioService
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
