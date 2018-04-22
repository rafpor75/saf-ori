import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Studenti } from './studenti.model';
import { StudentiPopupService } from './studenti-popup.service';
import { StudentiService } from './studenti.service';

@Component({
    selector: 'jhi-studenti-delete-dialog',
    templateUrl: './studenti-delete-dialog.component.html'
})
export class StudentiDeleteDialogComponent {

    studenti: Studenti;

    constructor(
        private studentiService: StudentiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.studentiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'studentiListModification',
                content: 'Deleted an studenti'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-studenti-delete-popup',
    template: ''
})
export class StudentiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private studentiPopupService: StudentiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.studentiPopupService
                .open(StudentiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
