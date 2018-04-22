import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tutor } from './tutor.model';
import { TutorPopupService } from './tutor-popup.service';
import { TutorService } from './tutor.service';

@Component({
    selector: 'jhi-tutor-delete-dialog',
    templateUrl: './tutor-delete-dialog.component.html'
})
export class TutorDeleteDialogComponent {

    tutor: Tutor;

    constructor(
        private tutorService: TutorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tutorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tutorListModification',
                content: 'Deleted an tutor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tutor-delete-popup',
    template: ''
})
export class TutorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tutorPopupService: TutorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tutorPopupService
                .open(TutorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
