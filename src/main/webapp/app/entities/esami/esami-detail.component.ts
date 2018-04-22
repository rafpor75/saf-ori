import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Esami } from './esami.model';
import { EsamiService } from './esami.service';

@Component({
    selector: 'jhi-esami-detail',
    templateUrl: './esami-detail.component.html'
})
export class EsamiDetailComponent implements OnInit, OnDestroy {

    esami: Esami;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private esamiService: EsamiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEsamis();
    }

    load(id) {
        this.esamiService.find(id)
            .subscribe((esamiResponse: HttpResponse<Esami>) => {
                this.esami = esamiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEsamis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'esamiListModification',
            (response) => this.load(this.esami.id)
        );
    }
}
