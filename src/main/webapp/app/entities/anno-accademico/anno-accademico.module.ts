import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    AnnoAccademicoService,
    AnnoAccademicoPopupService,
    AnnoAccademicoComponent,
    AnnoAccademicoDetailComponent,
    AnnoAccademicoDialogComponent,
    AnnoAccademicoPopupComponent,
    AnnoAccademicoDeletePopupComponent,
    AnnoAccademicoDeleteDialogComponent,
    annoAccademicoRoute,
    annoAccademicoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...annoAccademicoRoute,
    ...annoAccademicoPopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AnnoAccademicoComponent,
        AnnoAccademicoDetailComponent,
        AnnoAccademicoDialogComponent,
        AnnoAccademicoDeleteDialogComponent,
        AnnoAccademicoPopupComponent,
        AnnoAccademicoDeletePopupComponent,
    ],
    entryComponents: [
        AnnoAccademicoComponent,
        AnnoAccademicoDialogComponent,
        AnnoAccademicoPopupComponent,
        AnnoAccademicoDeleteDialogComponent,
        AnnoAccademicoDeletePopupComponent,
    ],
    providers: [
        AnnoAccademicoService,
        AnnoAccademicoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriAnnoAccademicoModule {}
