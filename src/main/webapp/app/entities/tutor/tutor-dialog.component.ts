import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tutor } from './tutor.model';
import { TutorPopupService } from './tutor-popup.service';
import { TutorService } from './tutor.service';

@Component({
    selector: 'jhi-tutor-dialog',
    templateUrl: './tutor-dialog.component.html'
})
export class TutorDialogComponent implements OnInit {

    tutor: Tutor;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tutorService: TutorService,
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
        if (this.tutor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tutorService.update(this.tutor));
        } else {
            this.subscribeToSaveResponse(
                this.tutorService.create(this.tutor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tutor>>) {
        result.subscribe((res: HttpResponse<Tutor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tutor) {
        this.eventManager.broadcast({ name: 'tutorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tutor-popup',
    template: ''
})
export class TutorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tutorPopupService: TutorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tutorPopupService
                    .open(TutorDialogComponent as Component, params['id']);
            } else {
                this.tutorPopupService
                    .open(TutorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
