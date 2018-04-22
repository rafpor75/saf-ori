import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Cdl } from './cdl.model';
import { CdlPopupService } from './cdl-popup.service';
import { CdlService } from './cdl.service';
import { Facolta, FacoltaService } from '../facolta';

@Component({
    selector: 'jhi-cdl-dialog',
    templateUrl: './cdl-dialog.component.html'
})
export class CdlDialogComponent implements OnInit {

    cdl: Cdl;
    isSaving: boolean;

    facoltas: Facolta[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private cdlService: CdlService,
        private facoltaService: FacoltaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.facoltaService.query()
            .subscribe((res: HttpResponse<Facolta[]>) => { this.facoltas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.cdl.id !== undefined) {
            this.subscribeToSaveResponse(
                this.cdlService.update(this.cdl));
        } else {
            this.subscribeToSaveResponse(
                this.cdlService.create(this.cdl));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Cdl>>) {
        result.subscribe((res: HttpResponse<Cdl>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Cdl) {
        this.eventManager.broadcast({ name: 'cdlListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFacoltaById(index: number, item: Facolta) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cdl-popup',
    template: ''
})
export class CdlPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cdlPopupService: CdlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.cdlPopupService
                    .open(CdlDialogComponent as Component, params['id']);
            } else {
                this.cdlPopupService
                    .open(CdlDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
