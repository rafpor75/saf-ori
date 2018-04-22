import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MaterieComponent } from './materie.component';
import { MaterieDetailComponent } from './materie-detail.component';
import { MateriePopupComponent } from './materie-dialog.component';
import { MaterieDeletePopupComponent } from './materie-delete-dialog.component';

@Injectable()
export class MaterieResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const materieRoute: Routes = [
    {
        path: 'materie',
        component: MaterieComponent,
        resolve: {
            'pagingParams': MaterieResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.materie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'materie/:id',
        component: MaterieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.materie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const materiePopupRoute: Routes = [
    {
        path: 'materie-new',
        component: MateriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.materie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materie/:id/edit',
        component: MateriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.materie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'materie/:id/delete',
        component: MaterieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.materie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
