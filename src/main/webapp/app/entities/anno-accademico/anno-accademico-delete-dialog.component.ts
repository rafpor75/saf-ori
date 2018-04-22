import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AnnoAccademico } from './anno-accademico.model';
import { AnnoAccademicoPopupService } from './anno-accademico-popup.service';
import { AnnoAccademicoService } from './anno-accademico.service';

@Component({
    selector: 'jhi-anno-accademico-delete-dialog',
    templateUrl: './anno-accademico-delete-dialog.component.html'
})
export class AnnoAccademicoDeleteDialogComponent {

    annoAccademico: AnnoAccademico;

    constructor(
        private annoAccademicoService: AnnoAccademicoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.annoAccademicoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'annoAccademicoListModification',
                content: 'Deleted an annoAccademico'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-anno-accademico-delete-popup',
    template: ''
})
export class AnnoAccademicoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private annoAccademicoPopupService: AnnoAccademicoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.annoAccademicoPopupService
                .open(AnnoAccademicoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
