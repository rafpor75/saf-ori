/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SafOriTestModule } from '../../../test.module';
import { TutorComponent } from '../../../../../../main/webapp/app/entities/tutor/tutor.component';
import { TutorService } from '../../../../../../main/webapp/app/entities/tutor/tutor.service';
import { Tutor } from '../../../../../../main/webapp/app/entities/tutor/tutor.model';

describe('Component Tests', () => {

    describe('Tutor Management Component', () => {
        let comp: TutorComponent;
        let fixture: ComponentFixture<TutorComponent>;
        let service: TutorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SafOriTestModule],
                declarations: [TutorComponent],
                providers: [
                    TutorService
                ]
            })
            .overrideTemplate(TutorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TutorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TutorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tutor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tutors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
