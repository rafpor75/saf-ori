import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Docenti } from './docenti.model';
import { DocentiService } from './docenti.service';

@Component({
    selector: 'jhi-docenti-detail',
    templateUrl: './docenti-detail.component.html'
})
export class DocentiDetailComponent implements OnInit, OnDestroy {

    docenti: Docenti;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private docentiService: DocentiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocentis();
    }

    load(id) {
        this.docentiService.find(id)
            .subscribe((docentiResponse: HttpResponse<Docenti>) => {
                this.docenti = docentiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocentis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'docentiListModification',
            (response) => this.load(this.docenti.id)
        );
    }
}
