/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { NoteEsameComponent } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.component';
import { NoteEsameService } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.service';
import { NoteEsame } from '../../../../../../main/webapp/app/entities/note-esame/note-esame.model';

describe('Component Tests', () => {

    describe('NoteEsame Management Component', () => {
        let comp: NoteEsameComponent;
        let fixture: ComponentFixture<NoteEsameComponent>;
        let service: NoteEsameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [NoteEsameComponent],
                providers: [
                    NoteEsameService
                ]
            })
            .overrideTemplate(NoteEsameComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NoteEsameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NoteEsameService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new NoteEsame(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.noteEsames[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
