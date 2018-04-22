import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Materie } from './materie.model';
import { MateriePopupService } from './materie-popup.service';
import { MaterieService } from './materie.service';

@Component({
    selector: 'jhi-materie-delete-dialog',
    templateUrl: './materie-delete-dialog.component.html'
})
export class MaterieDeleteDialogComponent {

    materie: Materie;

    constructor(
        private materieService: MaterieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.materieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'materieListModification',
                content: 'Deleted an materie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-materie-delete-popup',
    template: ''
})
export class MaterieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private materiePopupService: MateriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.materiePopupService
                .open(MaterieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
