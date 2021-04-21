import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PiwebBreadcrumbComponent } from '@shared/modules/modules/piweb-util/components';
import { PiwebFooterComponent } from './components/piweb-footer/piweb-footer.component';
import { PiwebIdentificadorObrigatoriedadeComponent } from '@shared/modules/modules/piweb-util/components';
import { PiwebHeaderModule } from './modules/piweb-header/piweb-header.module';
import { PiwebBreadcrumbObserver } from './services/piweb-breadcrumb.observer';
import { PiwebBreadcrumbService } from './services/piweb-breadcrumb.service';

@NgModule({
  declarations: [
    PiwebIdentificadorObrigatoriedadeComponent,
    PiwebBreadcrumbComponent,
    PiwebFooterComponent,
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    PiwebIdentificadorObrigatoriedadeComponent,
    PiwebBreadcrumbComponent,
    PiwebFooterComponent,
    PiwebHeaderModule,
  ],
  providers: [
    PiwebBreadcrumbService,
    PiwebBreadcrumbObserver
  ]
})
export class PiwebUtilModule { }
