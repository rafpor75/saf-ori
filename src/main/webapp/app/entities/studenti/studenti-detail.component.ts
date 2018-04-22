import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Studenti } from './studenti.model';
import { StudentiService } from './studenti.service';

@Component({
    selector: 'jhi-studenti-detail',
    templateUrl: './studenti-detail.component.html'
})
export class StudentiDetailComponent implements OnInit, OnDestroy {

    studenti: Studenti;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private studentiService: StudentiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStudentis();
    }

    load(id) {
        this.studentiService.find(id)
            .subscribe((studentiResponse: HttpResponse<Studenti>) => {
                this.studenti = studentiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStudentis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'studentiListModification',
            (response) => this.load(this.studenti.id)
        );
    }
}
