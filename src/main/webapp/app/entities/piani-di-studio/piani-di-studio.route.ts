import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { PianiDiStudioComponent } from './piani-di-studio.component';
import { PianiDiStudioDetailComponent } from './piani-di-studio-detail.component';
import { PianiDiStudioPopupComponent } from './piani-di-studio-dialog.component';
import { PianiDiStudioDeletePopupComponent } from './piani-di-studio-delete-dialog.component';

@Injectable()
export class PianiDiStudioResolvePagingParams implements Resolve<any> {

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

export const pianiDiStudioRoute: Routes = [
    {
        path: 'piani-di-studio',
        component: PianiDiStudioComponent,
        resolve: {
            'pagingParams': PianiDiStudioResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.pianiDiStudio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'piani-di-studio/:id',
        component: PianiDiStudioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.pianiDiStudio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pianiDiStudioPopupRoute: Routes = [
    {
        path: 'piani-di-studio-new',
        component: PianiDiStudioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.pianiDiStudio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piani-di-studio/:id/edit',
        component: PianiDiStudioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.pianiDiStudio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'piani-di-studio/:id/delete',
        component: PianiDiStudioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.pianiDiStudio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
