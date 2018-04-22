import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AnnoAccademico } from './anno-accademico.model';
import { AnnoAccademicoService } from './anno-accademico.service';

@Component({
    selector: 'jhi-anno-accademico-detail',
    templateUrl: './anno-accademico-detail.component.html'
})
export class AnnoAccademicoDetailComponent implements OnInit, OnDestroy {

    annoAccademico: AnnoAccademico;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private annoAccademicoService: AnnoAccademicoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAnnoAccademicos();
    }

    load(id) {
        this.annoAccademicoService.find(id)
            .subscribe((annoAccademicoResponse: HttpResponse<AnnoAccademico>) => {
                this.annoAccademico = annoAccademicoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAnnoAccademicos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'annoAccademicoListModification',
            (response) => this.load(this.annoAccademico.id)
        );
    }
}
