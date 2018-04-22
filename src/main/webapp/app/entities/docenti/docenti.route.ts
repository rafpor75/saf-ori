import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DocentiComponent } from './docenti.component';
import { DocentiDetailComponent } from './docenti-detail.component';
import { DocentiPopupComponent } from './docenti-dialog.component';
import { DocentiDeletePopupComponent } from './docenti-delete-dialog.component';

@Injectable()
export class DocentiResolvePagingParams implements Resolve<any> {

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

export const docentiRoute: Routes = [
    {
        path: 'docenti',
        component: DocentiComponent,
        resolve: {
            'pagingParams': DocentiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.docenti.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'docenti/:id',
        component: DocentiDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.docenti.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const docentiPopupRoute: Routes = [
    {
        path: 'docenti-new',
        component: DocentiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.docenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'docenti/:id/edit',
        component: DocentiPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.docenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'docenti/:id/delete',
        component: DocentiDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.docenti.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
