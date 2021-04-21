import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router, PRIMARY_OUTLET, UrlSegment } from '@angular/router';
import { filter } from 'rxjs/operators';
import { PiwebBreadCrumbModel } from '../../models/piweb-breadcrumb.model';
import { PiwebBreadcrumbObserver } from '../../services/piweb-breadcrumb.observer';
import { PiwebBreadcrumbService } from '../../services/piweb-breadcrumb.service';

@Component({
  selector: 'piweb-breadcrumb',
  templateUrl: './piweb-breadcrumb.component.html',
  styleUrls: ['./piweb-breadcrumb.component.css']
})
export class PiwebBreadcrumbComponent implements OnInit {
  breadcrumbs: Array<PiwebBreadCrumbModel> = new Array<PiwebBreadCrumbModel>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private breadcrumbObs: PiwebBreadcrumbObserver,
    private breadcrumbSvc: PiwebBreadcrumbService
  ) {
  }

  ngOnInit() {
    this.cadastrarEventos();
    setTimeout(() => {
      this.breadcrumbSvc.listarRotas();
    });
  }

  cadastrarEventos() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)).subscribe(() => {
      this.listarRotas();
    });

    this.breadcrumbObs.$listarRotas.subscribe(() => {
      this.listarRotas();
    });
  }

  private listarRotas() {
    const root: ActivatedRoute = this.activatedRoute.root;
    this.breadcrumbs = this.getBreadcrumbs(root);

  }

  private getBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: PiwebBreadCrumbModel[] = []): PiwebBreadCrumbModel[] {
    const ROUTE_DATA_BREADCRUMB = 'titulo';
    const ROUTE_USE_LINK_BREADCRUMB = 'ativarLink';
    const ROUTE_LINK_BREADCRUMB = 'link';
    const ROUTE_LIST_DATA_BREADCRUMB = 'breadcrumb';
    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {

      if (child.outlet !== PRIMARY_OUTLET) {
        continue;
      }

      if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB) && !child.snapshot.data.hasOwnProperty(ROUTE_LIST_DATA_BREADCRUMB)) {
        return this.getBreadcrumbs(child, url, breadcrumbs);
      }

      const routeURL: string = child.snapshot.url.map((segment: UrlSegment) => segment.path).join('/');

      url += `/${ routeURL }`;

      if (child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
        const breadcrumb: PiwebBreadCrumbModel = {
          titulo: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
          params: child.snapshot.params,
          link: child.snapshot.data[ROUTE_LINK_BREADCRUMB] || url,
          ativarLink: child.snapshot.data[ROUTE_USE_LINK_BREADCRUMB]
        };
        if (this.canAddBread(breadcrumbs, breadcrumb)) {
          breadcrumbs.push(breadcrumb);
        }
      }

      if (child.snapshot.data.hasOwnProperty(ROUTE_LIST_DATA_BREADCRUMB)) {
        const listaBreads = child.snapshot.data[ROUTE_LIST_DATA_BREADCRUMB] as [];
        for (let index = 0; index < listaBreads.length; index++) {
          const element = listaBreads[index] as { titulo: string, link: string, ativarLink: boolean };
          const breadcrumb: PiwebBreadCrumbModel = {
            titulo: element.titulo,
            params: child.snapshot.params,
            link: element.link || url,
            ativarLink: element.ativarLink
          };

          if (this.canAddBread(breadcrumbs, breadcrumb)) {
            breadcrumbs.push(breadcrumb);
          }
        }
      }

      return this.getBreadcrumbs(child, url, breadcrumbs);
    }
  }

  private canAddBread(breadcrumbs: PiwebBreadCrumbModel[], breadcrumb: PiwebBreadCrumbModel) {
    if (breadcrumb && breadcrumbs) {
      return breadcrumbs.find(x => x.titulo === breadcrumb.titulo) == undefined;
    }
    return false;
  }

}
