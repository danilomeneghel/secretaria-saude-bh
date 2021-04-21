import { Component, Input } from '@angular/core';

@Component({
  selector: 'piweb-header-menu-link',
  templateUrl: './piweb-header-menu-link.component.html',
  styleUrls: ['./piweb-header-menu-link.component.css']
})
export class PiwebHeaderMenuLinkComponent {
  @Input() href: string;
  constructor() { }


}
