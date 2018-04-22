/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SafOriTestModule } from '../../../test.module';
import { NoteEsameDetailComponent } from '../../../../../../main/webapp/app/entities/note-esame/note-esame-detail.component';
import { NoteEsameService } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.service';
import { NoteEsame } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.model';

describe('Component Tests', () => {

    describe('NoteEsame Management Detail Component', () => {
        let comp: NoteEsameDetailComponent;
        let fixture: ComponentFixture<NoteEsameDetailComponent>;
        let service: NoteEsameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [NoteEsameDetailComponent],
                providers: [
                    NoteEsameService
                ]
            })
            .overrideTemplate(NoteEsameDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NoteEsameDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoteEsameService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new NoteEsame(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.noteEsame).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
