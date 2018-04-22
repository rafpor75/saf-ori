import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Esami } from './esami.model';
import { EsamiPopupService } from './esami-popup.service';
import { EsamiService } from './esami.service';

@Component({
    selector: 'jhi-esami-delete-dialog',
    templateUrl: './esami-delete-dialog.component.html'
})
export class EsamiDeleteDialogComponent {

    esami: Esami;

    constructor(
        private esamiService: EsamiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.esamiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'esamiListModification',
                content: 'Deleted an esami'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-esami-delete-popup',
    template: ''
})
export class EsamiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private esamiPopupService: EsamiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.esamiPopupService
                .open(EsamiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
