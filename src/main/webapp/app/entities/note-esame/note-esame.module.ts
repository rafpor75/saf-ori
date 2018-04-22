import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SafOriSharedModule } from '../../shared';
import {
    NoteEsameService,
    NoteEsamePopupService,
    NoteEsameComponent,
    NoteEsameDetailComponent,
    NoteEsameDialogComponent,
    NoteEsamePopupComponent,
    NoteEsameDeletePopupComponent,
    NoteEsameDeleteDialogComponent,
    noteEsameRoute,
    noteEsamePopupRoute,
    NoteEsameResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...noteEsameRoute,
    ...noteEsamePopupRoute,
];

@NgModule({
    imports: [
        SafOriSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        NoteEsameComponent,
        NoteEsameDetailComponent,
        NoteEsameDialogComponent,
        NoteEsameDeleteDialogComponent,
        NoteEsamePopupComponent,
        NoteEsameDeletePopupComponent,
    ],
    entryComponents: [
        NoteEsameComponent,
        NoteEsameDialogComponent,
        NoteEsamePopupComponent,
        NoteEsameDeleteDialogComponent,
        NoteEsameDeletePopupComponent,
    ],
    providers: [
        NoteEsameService,
        NoteEsamePopupService,
        NoteEsameResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SafOriNoteEsameModule {}
