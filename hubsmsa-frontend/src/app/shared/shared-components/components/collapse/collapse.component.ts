import { Component, ContentChildren, Input, QueryList } from '@angular/core';
import { CollapseGroupDirective } from '@shared/components/components/collapse-group/collapse-group.directive';

@Component({
  selector: 'psapp-collapse',
  templateUrl: './collapse.component.html',
  styleUrls: ['./collapse.component.css']
})
export class CollapseComponent {
  @ContentChildren(CollapseGroupDirective) collapseGroupList: QueryList<CollapseGroupDirective>;
  @Input() closeOthers: boolean;
}
