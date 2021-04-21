import { AfterViewInit, Directive, ElementRef, Input, Renderer2 } from '@angular/core';
import { SecurityService } from '@core/authentication/security.service';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

@Directive({
  selector: '[identificadorPermissao]'
})
export class IdentificadorPermissaoDirective implements AfterViewInit {
  @Input('identificadorPermissao') permissoes: PermissaoUsuarioEnum | PermissaoUsuarioEnum[];

  constructor(
    private renderer: Renderer2,
    private elementRef: ElementRef,
    private securityService: SecurityService,
  ) {
  }

  ngAfterViewInit(): void {
    if (this.permissoes && !this.securityService.possuiPermissao(this.permissoes)) {
      this.renderer.setAttribute(this.elementRef.nativeElement, 'disabled', 'true');
    }
  }


}
