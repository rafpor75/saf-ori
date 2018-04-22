import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sedi } from './sedi.model';
import { SediPopupService } from './sedi-popup.service';
import { SediService } from './sedi.service';

@Component({
    selector: 'jhi-sedi-dialog',
    templateUrl: './sedi-dialog.component.html'
})
export class SediDialogComponent implements OnInit {

    sedi: Sedi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sediService: SediService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sedi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sediService.update(this.sedi));
        } else {
            this.subscribeToSaveResponse(
                this.sediService.create(this.sedi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Sedi>>) {
        result.subscribe((res: HttpResponse<Sedi>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Sedi) {
        this.eventManager.broadcast({ name: 'sediListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sedi-popup',
    template: ''
})
export class SediPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sediPopupService: SediPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sediPopupService
                    .open(SediDialogComponent as Component, params['id']);
            } else {
                this.sediPopupService
                    .open(SediDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
