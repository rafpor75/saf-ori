/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SafOriTestModule } from '../../../test.module';
import { NoteEsameDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/note-esame/note-esame-delete-dialog.component';
import { NoteEsameService } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.service';

describe('Component Tests', () => {

    describe('NoteEsame Management Delete Component', () => {
        let comp: NoteEsameDeleteDialogComponent;
        let fixture: ComponentFixture<NoteEsameDeleteDialogComponent>;
        let service: NoteEsameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [NoteEsameDeleteDialogComponent],
                providers: [
                    NoteEsameService
                ]
            })
            .overrideTemplate(NoteEsameDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NoteEsameDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoteEsameService);
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
