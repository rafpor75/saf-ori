import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NoteEsame } from './note-esame.model';
import { NoteEsamePopupService } from './note-esame-popup.service';
import { NoteEsameService } from './note-esame.service';

@Component({
    selector: 'jhi-note-esame-delete-dialog',
    templateUrl: './note-esame-delete-dialog.component.html'
})
export class NoteEsameDeleteDialogComponent {

    noteEsame: NoteEsame;

    constructor(
        private noteEsameService: NoteEsameService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.noteEsameService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'noteEsameListModification',
                content: 'Deleted an noteEsame'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-note-esame-delete-popup',
    template: ''
})
export class NoteEsameDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private noteEsamePopupService: NoteEsamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.noteEsamePopupService
                .open(NoteEsameDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
