import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';

@Component({
  selector: 'piweb-header-dropdown',
  templateUrl: './piweb-header-dropdown.component.html',
  styleUrls: ['./piweb-header-dropdown.component.css']
})
export class PiwebHeaderDropdownComponent implements AfterViewInit {
  @Input() titulo: string;
  @Input() customClass = '';

  @ViewChild('submenuParent') submenuParent: ElementRef;
  possuiSubmenu = true;

  constructor() {
  }

  ngAfterViewInit(): void {
    if (this.submenuParent) {
      setTimeout(() => this.possuiSubmenu = this.submenuParent && this.submenuParent.nativeElement.children.length > 0);
    }
  }
}
