import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Facolta } from './facolta.model';
import { FacoltaService } from './facolta.service';

@Component({
    selector: 'jhi-facolta-detail',
    templateUrl: './facolta-detail.component.html'
})
export class FacoltaDetailComponent implements OnInit, OnDestroy {

    facolta: Facolta;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private facoltaService: FacoltaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFacoltas();
    }

    load(id) {
        this.facoltaService.find(id)
            .subscribe((facoltaResponse: HttpResponse<Facolta>) => {
                this.facolta = facoltaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFacoltas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'facoltaListModification',
            (response) => this.load(this.facolta.id)
        );
    }
}
