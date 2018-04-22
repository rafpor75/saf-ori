import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Materie } from './materie.model';
import { MateriePopupService } from './materie-popup.service';
import { MaterieService } from './materie.service';
import { Cdl, CdlService } from '../cdl';
import { Tutor, TutorService } from '../tutor';
import { Docenti, DocentiService } from '../docenti';

@Component({
    selector: 'jhi-materie-dialog',
    templateUrl: './materie-dialog.component.html'
})
export class MaterieDialogComponent implements OnInit {

    materie: Materie;
    isSaving: boolean;

    cdls: Cdl[];

    tutors: Tutor[];

    docentis: Docenti[];
    dataModificaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private materieService: MaterieService,
        private cdlService: CdlService,
        private tutorService: TutorService,
        private docentiService: DocentiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cdlService.query()
            .subscribe((res: HttpResponse<Cdl[]>) => { this.cdls = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tutorService.query()
            .subscribe((res: HttpResponse<Tutor[]>) => { this.tutors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.docentiService.query()
            .subscribe((res: HttpResponse<Docenti[]>) => { this.docentis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.materie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.materieService.update(this.materie));
        } else {
            this.subscribeToSaveResponse(
                this.materieService.create(this.materie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Materie>>) {
        result.subscribe((res: HttpResponse<Materie>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Materie) {
        this.eventManager.broadcast({ name: 'materieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCdlById(index: number, item: Cdl) {
        return item.id;
    }

    trackTutorById(index: number, item: Tutor) {
        return item.id;
    }

    trackDocentiById(index: number, item: Docenti) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-materie-popup',
    template: ''
})
export class MateriePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materiePopupService: MateriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.materiePopupService
                    .open(MaterieDialogComponent as Component, params['id']);
            } else {
                this.materiePopupService
                    .open(MaterieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
