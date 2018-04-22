import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Docenti } from './docenti.model';
import { DocentiPopupService } from './docenti-popup.service';
import { DocentiService } from './docenti.service';

@Component({
    selector: 'jhi-docenti-delete-dialog',
    templateUrl: './docenti-delete-dialog.component.html'
})
export class DocentiDeleteDialogComponent {

    docenti: Docenti;

    constructor(
        private docentiService: DocentiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.docentiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'docentiListModification',
                content: 'Deleted an docenti'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-docenti-delete-popup',
    template: ''
})
export class DocentiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private docentiPopupService: DocentiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.docentiPopupService
                .open(DocentiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
