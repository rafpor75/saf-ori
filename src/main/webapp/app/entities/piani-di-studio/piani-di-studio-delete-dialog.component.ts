import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PianiDiStudio } from './piani-di-studio.model';
import { PianiDiStudioPopupService } from './piani-di-studio-popup.service';
import { PianiDiStudioService } from './piani-di-studio.service';

@Component({
    selector: 'jhi-piani-di-studio-delete-dialog',
    templateUrl: './piani-di-studio-delete-dialog.component.html'
})
export class PianiDiStudioDeleteDialogComponent {

    pianiDiStudio: PianiDiStudio;

    constructor(
        private pianiDiStudioService: PianiDiStudioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pianiDiStudioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pianiDiStudioListModification',
                content: 'Deleted an pianiDiStudio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-piani-di-studio-delete-popup',
    template: ''
})
export class PianiDiStudioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pianiDiStudioPopupService: PianiDiStudioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pianiDiStudioPopupService
                .open(PianiDiStudioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
