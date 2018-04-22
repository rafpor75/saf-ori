import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Cdl } from './cdl.model';
import { CdlPopupService } from './cdl-popup.service';
import { CdlService } from './cdl.service';

@Component({
    selector: 'jhi-cdl-delete-dialog',
    templateUrl: './cdl-delete-dialog.component.html'
})
export class CdlDeleteDialogComponent {

    cdl: Cdl;

    constructor(
        private cdlService: CdlService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cdlService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'cdlListModification',
                content: 'Deleted an cdl'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cdl-delete-popup',
    template: ''
})
export class CdlDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private cdlPopupService: CdlPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.cdlPopupService
                .open(CdlDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
