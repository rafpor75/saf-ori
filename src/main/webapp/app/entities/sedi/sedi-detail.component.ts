import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Sedi } from './sedi.model';
import { SediService } from './sedi.service';

@Component({
    selector: 'jhi-sedi-detail',
    templateUrl: './sedi-detail.component.html'
})
export class SediDetailComponent implements OnInit, OnDestroy {

    sedi: Sedi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sediService: SediService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSedis();
    }

    load(id) {
        this.sediService.find(id)
            .subscribe((sediResponse: HttpResponse<Sedi>) => {
                this.sedi = sediResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSedis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sediListModification',
            (response) => this.load(this.sedi.id)
        );
    }
}
