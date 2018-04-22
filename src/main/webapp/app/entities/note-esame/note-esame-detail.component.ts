import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { NoteEsame } from './note-esame.model';
import { NoteEsameService } from './note-esame.service';

@Component({
    selector: 'jhi-note-esame-detail',
    templateUrl: './note-esame-detail.component.html'
})
export class NoteEsameDetailComponent implements OnInit, OnDestroy {

    noteEsame: NoteEsame;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private noteEsameService: NoteEsameService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNoteEsames();
    }

    load(id) {
        this.noteEsameService.find(id)
            .subscribe((noteEsameResponse: HttpResponse<NoteEsame>) => {
                this.noteEsame = noteEsameResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNoteEsames() {
        this.eventSubscriber = this.eventManager.subscribe(
            'noteEsameListModification',
            (response) => this.load(this.noteEsame.id)
        );
    }
}
