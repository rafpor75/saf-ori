import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Sedi } from './sedi.model';
import { SediService } from './sedi.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sedi',
    templateUrl: './sedi.component.html'
})
export class SediComponent implements OnInit, OnDestroy {
sedis: Sedi[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sediService: SediService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sediService.query().subscribe(
            (res: HttpResponse<Sedi[]>) => {
                this.sedis = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSedis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sedi) {
        return item.id;
    }
    registerChangeInSedis() {
        this.eventSubscriber = this.eventManager.subscribe('sediListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
