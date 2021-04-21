import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'piweb-footer',
  templateUrl: './piweb-footer.component.html',
  styleUrls: ['./piweb-footer.component.css']
})
export class PiwebFooterComponent implements OnInit {

  @Input() versao: string;
  
  @Input() srcLogo: string;


  constructor() { }

  ngOnInit() {
  }

}
