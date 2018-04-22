/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SafOriTestModule } from '../../../test.module';
import { PianiDiStudioDialogComponent } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio-dialog.component';
import { PianiDiStudioService } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio.service';
import { PianiDiStudio } from '../../../../../../main/webapp/app/entities/piani-di-studio/piani-di-studio.model';
import { AnnoAccademicoService } from '../../../../../../main/webapp/app/entities/anno-accademico';
import { CdlService } from '../../../../../../main/webapp/app/entities/cdl';
import { MaterieService } from '../../../../../../main/webapp/app/entities/materie';

describe('Component Tests', () => {

    describe('PianiDiStudio Management Dialog Component', () => {
        let comp: PianiDiStudioDialogComponent;
        let fixture: ComponentFixture<PianiDiStudioDialogComponent>;
        let service: PianiDiStudioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [PianiDiStudioDialogComponent],
                providers: [
                    AnnoAccademicoService,
                    CdlService,
                    MaterieService,
                    PianiDiStudioService
                ]
            })
            .overrideTemplate(PianiDiStudioDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PianiDiStudioDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PianiDiStudioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PianiDiStudio(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pianiDiStudio = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pianiDiStudioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PianiDiStudio();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pianiDiStudio = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pianiDiStudioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
