import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { NgcAlertModuleConfig } from './models/ngc-alert-module-config.mode';
import { NgcAlertService } from './services/ngc-alert.service';

@NgModule({
    declarations: [],
    imports: [
        CommonModule
    ]
})
export class NgcAlertModule {
    static forRoot(alertConfig: NgcAlertModuleConfig): ModuleWithProviders {
        return {
            ngModule: NgcAlertModule,
            providers: [
                NgcAlertService,
                {
                    provide: 'config_alert',
                    useValue: alertConfig
                }]
        };
    }
}
