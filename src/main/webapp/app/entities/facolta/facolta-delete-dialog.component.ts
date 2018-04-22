import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Facolta } from './facolta.model';
import { FacoltaPopupService } from './facolta-popup.service';
import { FacoltaService } from './facolta.service';

@Component({
    selector: 'jhi-facolta-delete-dialog',
    templateUrl: './facolta-delete-dialog.component.html'
})
export class FacoltaDeleteDialogComponent {

    facolta: Facolta;

    constructor(
        private facoltaService: FacoltaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facoltaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facoltaListModification',
                content: 'Deleted an facolta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facolta-delete-popup',
    template: ''
})
export class FacoltaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facoltaPopupService: FacoltaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.facoltaPopupService
                .open(FacoltaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
