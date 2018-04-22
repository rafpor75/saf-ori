import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AnnoAccademicoComponent } from './anno-accademico.component';
import { AnnoAccademicoDetailComponent } from './anno-accademico-detail.component';
import { AnnoAccademicoPopupComponent } from './anno-accademico-dialog.component';
import { AnnoAccademicoDeletePopupComponent } from './anno-accademico-delete-dialog.component';

export const annoAccademicoRoute: Routes = [
    {
        path: 'anno-accademico',
        component: AnnoAccademicoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.annoAccademico.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'anno-accademico/:id',
        component: AnnoAccademicoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.annoAccademico.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const annoAccademicoPopupRoute: Routes = [
    {
        path: 'anno-accademico-new',
        component: AnnoAccademicoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.annoAccademico.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anno-accademico/:id/edit',
        component: AnnoAccademicoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.annoAccademico.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'anno-accademico/:id/delete',
        component: AnnoAccademicoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'safOriApp.annoAccademico.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
