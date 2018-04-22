/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SafOriTestModule } from '../../../test.module';
import { AnnoAccademicoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico-delete-dialog.component';
import { AnnoAccademicoService } from '../../../../../../main/webapp/app/entities/anno-accademico/anno-accademico.service';

describe('Component Tests', () => {

    describe('AnnoAccademico Management Delete Component', () => {
        let comp: AnnoAccademicoDeleteDialogComponent;
        let fixture: ComponentFixture<AnnoAccademicoDeleteDialogComponent>;
        let service: AnnoAccademicoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [AnnoAccademicoDeleteDialogComponent],
                providers: [
                    AnnoAccademicoService
                ]
            })
            .overrideTemplate(AnnoAccademicoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnnoAccademicoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnnoAccademicoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
