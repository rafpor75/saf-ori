import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AnnoAccademico } from './anno-accademico.model';
import { AnnoAccademicoService } from './anno-accademico.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-anno-accademico',
    templateUrl: './anno-accademico.component.html'
})
export class AnnoAccademicoComponent implements OnInit, OnDestroy {
annoAccademicos: AnnoAccademico[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private annoAccademicoService: AnnoAccademicoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.annoAccademicoService.query().subscribe(
            (res: HttpResponse<AnnoAccademico[]>) => {
                this.annoAccademicos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnnoAccademicos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AnnoAccademico) {
        return item.id;
    }
    registerChangeInAnnoAccademicos() {
        this.eventSubscriber = this.eventManager.subscribe('annoAccademicoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
