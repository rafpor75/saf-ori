import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PianiDiStudio } from './piani-di-studio.model';
import { PianiDiStudioService } from './piani-di-studio.service';

@Component({
    selector: 'jhi-piani-di-studio-detail',
    templateUrl: './piani-di-studio-detail.component.html'
})
export class PianiDiStudioDetailComponent implements OnInit, OnDestroy {

    pianiDiStudio: PianiDiStudio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pianiDiStudioService: PianiDiStudioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPianiDiStudios();
    }

    load(id) {
        this.pianiDiStudioService.find(id)
            .subscribe((pianiDiStudioResponse: HttpResponse<PianiDiStudio>) => {
                this.pianiDiStudio = pianiDiStudioResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPianiDiStudios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pianiDiStudioListModification',
            (response) => this.load(this.pianiDiStudio.id)
        );
    }
}
