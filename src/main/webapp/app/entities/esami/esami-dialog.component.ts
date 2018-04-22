import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Esami } from './esami.model';
import { EsamiPopupService } from './esami-popup.service';
import { EsamiService } from './esami.service';
import { Sedi, SediService } from '../sedi';
import { Materie, MaterieService } from '../materie';

@Component({
    selector: 'jhi-esami-dialog',
    templateUrl: './esami-dialog.component.html'
})
export class EsamiDialogComponent implements OnInit {

    esami: Esami;
    isSaving: boolean;

    relesamisedis: Sedi[];

    materies: Materie[];
    dataDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private esamiService: EsamiService,
        private sediService: SediService,
        private materieService: MaterieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.sediService
            .query({filter: 'esami-is-null'})
            .subscribe((res: HttpResponse<Sedi[]>) => {
                if (!this.esami.relEsamiSediId) {
                    this.relesamisedis = res.body;
                } else {
                    this.sediService
                        .find(this.esami.relEsamiSediId)
                        .subscribe((subRes: HttpResponse<Sedi>) => {
                            this.relesamisedis = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.materieService.query()
            .subscribe((res: HttpResponse<Materie[]>) => { this.materies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.esami.id !== undefined) {
            this.subscribeToSaveResponse(
                this.esamiService.update(this.esami));
        } else {
            this.subscribeToSaveResponse(
                this.esamiService.create(this.esami));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Esami>>) {
        result.subscribe((res: HttpResponse<Esami>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Esami) {
        this.eventManager.broadcast({ name: 'esamiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSediById(index: number, item: Sedi) {
        return item.id;
    }

    trackMaterieById(index: number, item: Materie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-esami-popup',
    template: ''
})
export class EsamiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private esamiPopupService: EsamiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.esamiPopupService
                    .open(EsamiDialogComponent as Component, params['id']);
            } else {
                this.esamiPopupService
                    .open(EsamiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
