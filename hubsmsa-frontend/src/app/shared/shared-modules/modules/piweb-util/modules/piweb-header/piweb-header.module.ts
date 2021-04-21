import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PiwebHeaderMenuBarComponent } from './components/piweb-header-menu-bar/piweb-header-menu-bar.component';
import { PiwebHeaderComponent } from './components/piweb-header/piweb-header.component';
import { PiwebHeaderMenuLinkComponent } from './components/piweb-header-menu-link/piweb-header-menu-link.component';
import { PiwebHeaderDropdownComponent } from './components/piweb-header-dropdown/piweb-header-dropdown.component';
import { PiwebHeaderDropdownLinkDirective } from './directives/piweb-header-dropdown-link.directive';
import { PiwebHeaderSubDropComponent } from './components/piweb-header-sub-drop/piweb-header-sub-drop.component';

@NgModule({
  declarations: [
    PiwebHeaderMenuBarComponent,
    PiwebHeaderComponent,
    PiwebHeaderMenuLinkComponent,
    PiwebHeaderDropdownComponent,
    PiwebHeaderDropdownLinkDirective,
    PiwebHeaderSubDropComponent
  ],
  imports: [
    RouterModule, // TEMP
    CommonModule
  ],
  exports: [
    PiwebHeaderMenuBarComponent,
    PiwebHeaderComponent,
    PiwebHeaderMenuLinkComponent,
    PiwebHeaderDropdownComponent,
    PiwebHeaderDropdownLinkDirective,
    PiwebHeaderSubDropComponent
  ]
})
export class PiwebHeaderModule {
}
