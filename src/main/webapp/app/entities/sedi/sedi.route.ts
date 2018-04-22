import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SediComponent } from './sedi.component';
import { SediDetailComponent } from './sedi-detail.component';
import { SediPopupComponent } from './sedi-dialog.component';
import { SediDeletePopupComponent } from './sedi-delete-dialog.component';

export const sediRoute: Routes = [
    {
        path: 'sedi',
        component: SediComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.sedi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sedi/:id',
        component: SediDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.sedi.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sediPopupRoute: Routes = [
    {
        path: 'sedi-new',
        component: SediPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.sedi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sedi/:id/edit',
        component: SediPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.sedi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sedi/:id/delete',
        component: SediDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.sedi.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
