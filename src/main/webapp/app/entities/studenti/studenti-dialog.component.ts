import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Studenti } from './studenti.model';
import { StudentiPopupService } from './studenti-popup.service';
import { StudentiService } from './studenti.service';
import { Cdl, CdlService } from '../cdl';

@Component({
    selector: 'jhi-studenti-dialog',
    templateUrl: './studenti-dialog.component.html'
})
export class StudentiDialogComponent implements OnInit {

    studenti: Studenti;
    isSaving: boolean;

    cdls: Cdl[];
    dataDiNascitaDp: any;
    dataModificaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private studentiService: StudentiService,
        private cdlService: CdlService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.cdlService.query()
            .subscribe((res: HttpResponse<Cdl[]>) => { this.cdls = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.studenti.id !== undefined) {
            this.subscribeToSaveResponse(
                this.studentiService.update(this.studenti));
        } else {
            this.subscribeToSaveResponse(
                this.studentiService.create(this.studenti));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Studenti>>) {
        result.subscribe((res: HttpResponse<Studenti>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Studenti) {
        this.eventManager.broadcast({ name: 'studentiListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-studenti-popup',
    template: ''
})
export class StudentiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentiPopupService: StudentiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.studentiPopupService
                    .open(StudentiDialogComponent as Component, params['id']);
            } else {
                this.studentiPopupService
                    .open(StudentiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
