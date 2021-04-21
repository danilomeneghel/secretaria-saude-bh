import { AfterViewInit, Directive, ElementRef, Renderer2 } from '@angular/core';
import { SecurityService } from '@core/authentication/security.service';
import { routes } from '../../../../../../../modules/private/private-routing.module';

@Directive({
  selector: '[piweb-header-dropdown-link]'
})
export class PiwebHeaderDropdownLinkDirective implements AfterViewInit {

  constructor(
    private renderer: Renderer2,
    private element: ElementRef,
    private securityService: SecurityService,
  ) {
    this.atribuirClass();
  }

  atribuirClass() {
    this.renderer.addClass(this.element.nativeElement, 'dropdown-item');
  }

  ngAfterViewInit(): void {
    const routerLinkElement = this.element.nativeElement.getAttribute('href').split('/')[2];
    const elementoRota = routes[0].children.find(item => item.path === routerLinkElement);
    const permissoes = elementoRota ? elementoRota.data.permissoesNecessarias : [];

    if (permissoes && !this.securityService.possuiPermissao(permissoes)) {
      this.renderer.removeChild(this.element.nativeElement.parentElement, this.element.nativeElement);
    }
  }

}
