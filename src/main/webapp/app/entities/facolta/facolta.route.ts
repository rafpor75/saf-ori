import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FacoltaComponent } from './facolta.component';
import { FacoltaDetailComponent } from './facolta-detail.component';
import { FacoltaPopupComponent } from './facolta-dialog.component';
import { FacoltaDeletePopupComponent } from './facolta-delete-dialog.component';

@Injectable()
export class FacoltaResolvePagingParams implements Resolve<any> {

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

export const facoltaRoute: Routes = [
    {
        path: 'facolta',
        component: FacoltaComponent,
        resolve: {
            'pagingParams': FacoltaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.facolta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'facolta/:id',
        component: FacoltaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.facolta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facoltaPopupRoute: Routes = [
    {
        path: 'facolta-new',
        component: FacoltaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.facolta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facolta/:id/edit',
        component: FacoltaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.facolta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facolta/:id/delete',
        component: FacoltaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.facolta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
