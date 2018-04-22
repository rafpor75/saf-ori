import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Cdl } from './cdl.model';
import { CdlService } from './cdl.service';

@Component({
    selector: 'jhi-cdl-detail',
    templateUrl: './cdl-detail.component.html'
})
export class CdlDetailComponent implements OnInit, OnDestroy {

    cdl: Cdl;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cdlService: CdlService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCdls();
    }

    load(id) {
        this.cdlService.find(id)
            .subscribe((cdlResponse: HttpResponse<Cdl>) => {
                this.cdl = cdlResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCdls() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cdlListModification',
            (response) => this.load(this.cdl.id)
        );
    }
}
