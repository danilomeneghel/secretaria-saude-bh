import { Directive, TemplateRef, ViewContainerRef, Input } from '@angular/core';
import { SecurityService } from '@core/authentication/security.service';
import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

@Directive({
    selector: '[verificadorPermissao]'
})
export class VerificadorPermissaoDirective {
    constructor(private templateRef: TemplateRef<any>,
                private viewContainer: ViewContainerRef,
                private securityService: SecurityService,
                ) {
    }

    @Input() set verificadorPermissao(permissoes: PermissaoUsuarioEnum | PermissaoUsuarioEnum[]) {
        if (permissoes && this.securityService.possuiPermissao(permissoes)) {
            this.viewContainer.createEmbeddedView(this.templateRef);
        } else {
            this.viewContainer.clear();
        }
    }
}