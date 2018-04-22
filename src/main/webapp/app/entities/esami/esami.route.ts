import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { EsamiComponent } from './esami.component';
import { EsamiDetailComponent } from './esami-detail.component';
import { EsamiPopupComponent } from './esami-dialog.component';
import { EsamiDeletePopupComponent } from './esami-delete-dialog.component';

@Injectable()
export class EsamiResolvePagingParams implements Resolve<any> {

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

export const esamiRoute: Routes = [
    {
        path: 'esami',
        component: EsamiComponent,
        resolve: {
            'pagingParams': EsamiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.esami.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'esami/:id',
        component: EsamiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.esami.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const esamiPopupRoute: Routes = [
    {
        path: 'esami-new',
        component: EsamiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.esami.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'esami/:id/edit',
        component: EsamiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.esami.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'esami/:id/delete',
        component: EsamiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.esami.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
