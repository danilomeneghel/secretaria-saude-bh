import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthControlService } from '@core/authentication/auth-control.service';
import { CredenciaisModel } from '@shared/models/interface/credenciais.model';
import { NgcAlertService } from '@shared/modules/modules/ngc-alert/services/ngc-alert.service';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'sbapp-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  formLogin: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private authControl: AuthControlService,
    private ngcAlert: NgcAlertService,
    private router: Router
  ) { }

  ngOnInit() {
    this.criarFormulario();
  }
  /**
   * Criar o formulário de login
   */
  criarFormulario(): void {
    this.formLogin = this.formBuilder.group({
      usuario: [null, [Validators.required]],
      senha: [null, [Validators.required]]
    });
  }

  doLogin() {
    try {
      this.validarLogin();
      const credenciais: CredenciaisModel = this.formLogin.getRawValue();
      this.loginService.relizarLogin(credenciais).subscribe((userLogged) => {
        this.authControl.setUsuarioLogado(userLogged);
        this.router.navigate(['admin']);
      });
    } catch (error) {
      this.ngcAlert.error(error.message, {
        noFocus: true
      });
    }
  }


  validarLogin() {
    if (this.formLogin.invalid) {
      throw new Error('Formulário inválido');
    }
  }
}
