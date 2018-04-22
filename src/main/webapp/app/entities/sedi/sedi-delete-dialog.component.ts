import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sedi } from './sedi.model';
import { SediPopupService } from './sedi-popup.service';
import { SediService } from './sedi.service';

@Component({
    selector: 'jhi-sedi-delete-dialog',
    templateUrl: './sedi-delete-dialog.component.html'
})
export class SediDeleteDialogComponent {

    sedi: Sedi;

    constructor(
        private sediService: SediService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sediService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sediListModification',
                content: 'Deleted an sedi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sedi-delete-popup',
    template: ''
})
export class SediDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sediPopupService: SediPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sediPopupService
                .open(SediDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
