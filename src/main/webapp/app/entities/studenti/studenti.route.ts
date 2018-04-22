import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StudentiComponent } from './studenti.component';
import { StudentiDetailComponent } from './studenti-detail.component';
import { StudentiPopupComponent } from './studenti-dialog.component';
import { StudentiDeletePopupComponent } from './studenti-delete-dialog.component';

@Injectable()
export class StudentiResolvePagingParams implements Resolve<any> {

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

export const studentiRoute: Routes = [
    {
        path: 'studenti',
        component: StudentiComponent,
        resolve: {
            'pagingParams': StudentiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.studenti.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'studenti/:id',
        component: StudentiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.studenti.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const studentiPopupRoute: Routes = [
    {
        path: 'studenti-new',
        component: StudentiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.studenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'studenti/:id/edit',
        component: StudentiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.studenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'studenti/:id/delete',
        component: StudentiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.studenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
