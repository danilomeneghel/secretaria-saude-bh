import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthControlService } from '@core/authentication/auth-control.service';
import { filter } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'sbapp-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  subscription: Subscription;
  constructor(
    private authControl: AuthControlService,
    private router: Router
  ) { }

  ngOnInit() {
    this.cadastrarEventos();
    this.verificarLogin();
  }
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
  cadastrarEventos(): any {
    this.subscription = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)).subscribe(() => {
        this.verificarLogin();
      });
  }

  verificarLogin(): any {
    if (!this.authControl.isLogged()) {
      this.router.navigate(['hubsmsa-servicos/login']);
    }
  }

}
