import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NoteEsame } from './note-esame.model';
import { NoteEsamePopupService } from './note-esame-popup.service';
import { NoteEsameService } from './note-esame.service';
import { Studenti, StudentiService } from '../studenti';
import { Esami, EsamiService } from '../esami';

@Component({
    selector: 'jhi-note-esame-dialog',
    templateUrl: './note-esame-dialog.component.html'
})
export class NoteEsameDialogComponent implements OnInit {

    noteEsame: NoteEsame;
    isSaving: boolean;

    studentis: Studenti[];

    esamis: Esami[];
    dataDispensaDp: any;
    dataCorsiDp: any;
    dataABIDp: any;
    dataRiepilogoDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private noteEsameService: NoteEsameService,
        private studentiService: StudentiService,
        private esamiService: EsamiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.studentiService.query()
            .subscribe((res: HttpResponse<Studenti[]>) => { this.studentis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.esamiService.query()
            .subscribe((res: HttpResponse<Esami[]>) => { this.esamis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.noteEsame.id !== undefined) {
            this.subscribeToSaveResponse(
                this.noteEsameService.update(this.noteEsame));
        } else {
            this.subscribeToSaveResponse(
                this.noteEsameService.create(this.noteEsame));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<NoteEsame>>) {
        result.subscribe((res: HttpResponse<NoteEsame>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: NoteEsame) {
        this.eventManager.broadcast({ name: 'noteEsameListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStudentiById(index: number, item: Studenti) {
        return item.id;
    }

    trackEsamiById(index: number, item: Esami) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-note-esame-popup',
    template: ''
})
export class NoteEsamePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private noteEsamePopupService: NoteEsamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.noteEsamePopupService
                    .open(NoteEsameDialogComponent as Component, params['id']);
            } else {
                this.noteEsamePopupService
                    .open(NoteEsameDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
