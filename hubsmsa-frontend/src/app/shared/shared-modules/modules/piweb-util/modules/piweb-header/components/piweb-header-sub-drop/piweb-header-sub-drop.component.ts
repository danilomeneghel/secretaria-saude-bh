import { AfterViewInit, Component, ElementRef, Input, ViewChild, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'piweb-header-sub-drop',
  templateUrl: './piweb-header-sub-drop.component.html',
  styleUrls: ['./piweb-header-sub-drop.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PiwebHeaderSubDropComponent implements AfterViewInit {
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
