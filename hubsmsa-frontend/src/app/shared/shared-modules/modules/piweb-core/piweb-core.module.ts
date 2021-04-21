import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { PiwebAppComponent } from './piweb-app/piweb-app.component';
@NgModule({
  declarations: [
    PiwebAppComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PiwebAppComponent
  ]
})
export class PiwebCoreModule { }
