import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ModuleWithProviders } from '@angular/compiler/src/core';
import { EmpresaService } from './empresa.service';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ]
})
export class SharedServicesModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedServicesModule,
      providers: [EmpresaService]
    };
  }
}
