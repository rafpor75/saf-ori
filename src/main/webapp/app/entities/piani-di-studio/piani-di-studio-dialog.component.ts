import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PianiDiStudio } from './piani-di-studio.model';
import { PianiDiStudioPopupService } from './piani-di-studio-popup.service';
import { PianiDiStudioService } from './piani-di-studio.service';
import { AnnoAccademico, AnnoAccademicoService } from '../anno-accademico';
import { Cdl, CdlService } from '../cdl';
import { Materie, MaterieService } from '../materie';

@Component({
    selector: 'jhi-piani-di-studio-dialog',
    templateUrl: './piani-di-studio-dialog.component.html'
})
export class PianiDiStudioDialogComponent implements OnInit {

    pianiDiStudio: PianiDiStudio;
    isSaving: boolean;

    annoaccademicos: AnnoAccademico[];

    cdls: Cdl[];

    materies: Materie[];
    dataModificaDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pianiDiStudioService: PianiDiStudioService,
        private annoAccademicoService: AnnoAccademicoService,
        private cdlService: CdlService,
        private materieService: MaterieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.annoAccademicoService.query()
            .subscribe((res: HttpResponse<AnnoAccademico[]>) => { this.annoaccademicos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.cdlService.query()
            .subscribe((res: HttpResponse<Cdl[]>) => { this.cdls = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.materieService.query()
            .subscribe((res: HttpResponse<Materie[]>) => { this.materies = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pianiDiStudio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pianiDiStudioService.update(this.pianiDiStudio));
        } else {
            this.subscribeToSaveResponse(
                this.pianiDiStudioService.create(this.pianiDiStudio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PianiDiStudio>>) {
        result.subscribe((res: HttpResponse<PianiDiStudio>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PianiDiStudio) {
        this.eventManager.broadcast({ name: 'pianiDiStudioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAnnoAccademicoById(index: number, item: AnnoAccademico) {
        return item.id;
    }

    trackCdlById(index: number, item: Cdl) {
        return item.id;
    }

    trackMaterieById(index: number, item: Materie) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-piani-di-studio-popup',
    template: ''
})
export class PianiDiStudioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pianiDiStudioPopupService: PianiDiStudioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pianiDiStudioPopupService
                    .open(PianiDiStudioDialogComponent as Component, params['id']);
            } else {
                this.pianiDiStudioPopupService
                    .open(PianiDiStudioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
