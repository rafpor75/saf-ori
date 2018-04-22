import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Docenti } from './docenti.model';
import { DocentiPopupService } from './docenti-popup.service';
import { DocentiService } from './docenti.service';

@Component({
    selector: 'jhi-docenti-dialog',
    templateUrl: './docenti-dialog.component.html'
})
export class DocentiDialogComponent implements OnInit {

    docenti: Docenti;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private docentiService: DocentiService,
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
        if (this.docenti.id !== undefined) {
            this.subscribeToSaveResponse(
                this.docentiService.update(this.docenti));
        } else {
            this.subscribeToSaveResponse(
                this.docentiService.create(this.docenti));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Docenti>>) {
        result.subscribe((res: HttpResponse<Docenti>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Docenti) {
        this.eventManager.broadcast({ name: 'docentiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-docenti-popup',
    template: ''
})
export class DocentiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private docentiPopupService: DocentiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.docentiPopupService
                    .open(DocentiDialogComponent as Component, params['id']);
            } else {
                this.docentiPopupService
                    .open(DocentiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
