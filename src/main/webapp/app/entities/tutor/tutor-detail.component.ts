import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tutor } from './tutor.model';
import { TutorService } from './tutor.service';

@Component({
    selector: 'jhi-tutor-detail',
    templateUrl: './tutor-detail.component.html'
})
export class TutorDetailComponent implements OnInit, OnDestroy {

    tutor: Tutor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tutorService: TutorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTutors();
    }

    load(id) {
        this.tutorService.find(id)
            .subscribe((tutorResponse: HttpResponse<Tutor>) => {
                this.tutor = tutorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTutors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tutorListModification',
            (response) => this.load(this.tutor.id)
        );
    }
}
