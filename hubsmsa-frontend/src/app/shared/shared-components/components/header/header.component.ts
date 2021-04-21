import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import * as $ from 'jquery';

@Component({
  selector: 'psapp-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(
    private keycloakService: KeycloakService
  ) {

    $(document).ready(() => {
      $('.nav-link.dropdown-toggle').on('click', function() {
        $(this).next('.dropdown-menu').find('.dropdown-submenu .dropdown-menu').each(function() {
          $(this).hide();
        });
      });

      $('.dropdown-submenu a.link-submenu').on('click', function(e) {
        e.stopPropagation();
        e.preventDefault();

        const menuToOpen = $(this).next('.dropdown-menu');

        $('.dropdown-submenu .dropdown-menu').each(function() {
          if ($(this)[0] !== menuToOpen[0]) {
            $(this).hide();
          }
        });

        $(this).next('.dropdown-menu').toggle();
      });

    });

  }

  getUserName() {
    return this.keycloakService.isLoggedIn() ? this.keycloakService.getUsername() : '';
  }

  sair() {
    this.keycloakService.logout();
  }


}
