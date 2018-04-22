import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Facolta } from './facolta.model';
import { FacoltaPopupService } from './facolta-popup.service';
import { FacoltaService } from './facolta.service';

@Component({
    selector: 'jhi-facolta-dialog',
    templateUrl: './facolta-dialog.component.html'
})
export class FacoltaDialogComponent implements OnInit {

    facolta: Facolta;
    isSaving: boolean;
    dataModificaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private facoltaService: FacoltaService,
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
        if (this.facolta.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facoltaService.update(this.facolta));
        } else {
            this.subscribeToSaveResponse(
                this.facoltaService.create(this.facolta));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Facolta>>) {
        result.subscribe((res: HttpResponse<Facolta>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Facolta) {
        this.eventManager.broadcast({ name: 'facoltaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-facolta-popup',
    template: ''
})
export class FacoltaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facoltaPopupService: FacoltaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.facoltaPopupService
                    .open(FacoltaDialogComponent as Component, params['id']);
            } else {
                this.facoltaPopupService
                    .open(FacoltaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
