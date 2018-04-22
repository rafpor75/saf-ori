import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { NoteEsameComponent } from './note-esame.component';
import { NoteEsameDetailComponent } from './note-esame-detail.component';
import { NoteEsamePopupComponent } from './note-esame-dialog.component';
import { NoteEsameDeletePopupComponent } from './note-esame-delete-dialog.component';

@Injectable()
export class NoteEsameResolvePagingParams implements Resolve<any> {

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

export const noteEsameRoute: Routes = [
    {
        path: 'note-esame',
        component: NoteEsameComponent,
        resolve: {
            'pagingParams': NoteEsameResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.noteEsame.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'note-esame/:id',
        component: NoteEsameDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.noteEsame.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const noteEsamePopupRoute: Routes = [
    {
        path: 'note-esame-new',
        component: NoteEsamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.noteEsame.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'note-esame/:id/edit',
        component: NoteEsamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.noteEsame.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'note-esame/:id/delete',
        component: NoteEsameDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.noteEsame.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
