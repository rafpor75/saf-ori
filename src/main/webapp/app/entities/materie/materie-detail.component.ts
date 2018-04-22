import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Materie } from './materie.model';
import { MaterieService } from './materie.service';

@Component({
    selector: 'jhi-materie-detail',
    templateUrl: './materie-detail.component.html'
})
export class MaterieDetailComponent implements OnInit, OnDestroy {

    materie: Materie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private materieService: MaterieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMateries();
    }

    load(id) {
        this.materieService.find(id)
            .subscribe((materieResponse: HttpResponse<Materie>) => {
                this.materie = materieResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMateries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'materieListModification',
            (response) => this.load(this.materie.id)
        );
    }
}
