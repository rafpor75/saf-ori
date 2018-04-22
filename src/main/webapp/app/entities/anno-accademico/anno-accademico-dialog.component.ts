import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnnoAccademico } from './anno-accademico.model';
import { AnnoAccademicoPopupService } from './anno-accademico-popup.service';
import { AnnoAccademicoService } from './anno-accademico.service';

@Component({
    selector: 'jhi-anno-accademico-dialog',
    templateUrl: './anno-accademico-dialog.component.html'
})
export class AnnoAccademicoDialogComponent implements OnInit {

    annoAccademico: AnnoAccademico;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private annoAccademicoService: AnnoAccademicoService,
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
        if (this.annoAccademico.id !== undefined) {
            this.subscribeToSaveResponse(
                this.annoAccademicoService.update(this.annoAccademico));
        } else {
            this.subscribeToSaveResponse(
                this.annoAccademicoService.create(this.annoAccademico));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AnnoAccademico>>) {
        result.subscribe((res: HttpResponse<AnnoAccademico>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AnnoAccademico) {
        this.eventManager.broadcast({ name: 'annoAccademicoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-anno-accademico-popup',
    template: ''
})
export class AnnoAccademicoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annoAccademicoPopupService: AnnoAccademicoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.annoAccademicoPopupService
                    .open(AnnoAccademicoDialogComponent as Component, params['id']);
            } else {
                this.annoAccademicoPopupService
                    .open(AnnoAccademicoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
