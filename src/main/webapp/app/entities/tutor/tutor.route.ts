import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TutorComponent } from './tutor.component';
import { TutorDetailComponent } from './tutor-detail.component';
import { TutorPopupComponent } from './tutor-dialog.component';
import { TutorDeletePopupComponent } from './tutor-delete-dialog.component';

@Injectable()
export class TutorResolvePagingParams implements Resolve<any> {

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

export const tutorRoute: Routes = [
    {
        path: 'tutor',
        component: TutorComponent,
        resolve: {
            'pagingParams': TutorResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.tutor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tutor/:id',
        component: TutorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.tutor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tutorPopupRoute: Routes = [
    {
        path: 'tutor-new',
        component: TutorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.tutor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tutor/:id/edit',
        component: TutorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.tutor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tutor/:id/delete',
        component: TutorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.tutor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
