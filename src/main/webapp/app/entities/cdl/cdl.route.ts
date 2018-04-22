import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CdlComponent } from './cdl.component';
import { CdlDetailComponent } from './cdl-detail.component';
import { CdlPopupComponent } from './cdl-dialog.component';
import { CdlDeletePopupComponent } from './cdl-delete-dialog.component';

@Injectable()
export class CdlResolvePagingParams implements Resolve<any> {

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

export const cdlRoute: Routes = [
    {
        path: 'cdl',
        component: CdlComponent,
        resolve: {
            'pagingParams': CdlResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.cdl.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cdl/:id',
        component: CdlDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.cdl.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const cdlPopupRoute: Routes = [
    {
        path: 'cdl-new',
        component: CdlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.cdl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cdl/:id/edit',
        component: CdlPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.cdl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cdl/:id/delete',
        component: CdlDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.cdl.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
